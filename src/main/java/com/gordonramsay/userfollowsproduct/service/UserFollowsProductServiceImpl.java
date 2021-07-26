package com.gordonramsay.userfollowsproduct.service;

import com.alibaba.fastjson.JSON;
import com.gordonramsay.userfollowsproduct.dto.UserFollowsProductRequest;
import com.gordonramsay.userfollowsproduct.model.FollowedProduct;
import com.gordonramsay.userfollowsproduct.model.NotificationMsg;
import com.gordonramsay.userfollowsproduct.model.NotificationType;
import com.gordonramsay.userfollowsproduct.repository.FollowedProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserFollowsProductServiceImpl implements UserFollowsProductService {
    private static final String NOTIFICATION_TOPIC = "notification";

    private final FollowedProductRepository followedProductRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public List<FollowedProduct> getAll() {
        List<FollowedProduct> followedProducts = new ArrayList<>();
        followedProductRepository.findAll().forEach(followedProducts::add);
        return followedProducts;
    }

    @Override
    public void updateFollowedProduct(FollowedProduct product) {
        String barcode = product.getBarcode();

        FollowedProduct oldProduct = followedProductRepository.findById(barcode).orElseThrow(RuntimeException::new);
        // TODO: Should we check null parameters, do we want to update fields as null

        Double newSalesPrice = product.getSalesPrice();
        Double newMobilePrice = product.getMobileSalesPrice();
        Double oldMobilePrice = oldProduct.getMobileSalesPrice();
        Double oldSalesPrice = oldProduct.getSalesPrice();

        boolean isMobilePriceDecreased = isPriceDecreased(oldMobilePrice, newMobilePrice);
        boolean isSalesPriceDecreased = isPriceDecreased(oldSalesPrice, newSalesPrice);

        Set<String> followerIds = oldProduct.getFollowerIds();

        if (isMobilePriceDecreased) {
            sendPriceDecreasedNotification(followerIds, NotificationType.PUSH, oldMobilePrice, newMobilePrice);
        }

        if (isSalesPriceDecreased) {
            sendPriceDecreasedNotification(followerIds, NotificationType.EMAIL, oldSalesPrice, newSalesPrice);
        }

        oldProduct.setSalesPrice(newSalesPrice);
        oldProduct.setMobileSalesPrice(newMobilePrice);

        // Update the product with the updated product information
        followedProductRepository.save(oldProduct);
    }

    @Override
    public void saveFollowedProduct(FollowedProduct product) {
        followedProductRepository.save(product);
    }

    @Override
    public FollowedProduct followProduct(UserFollowsProductRequest request) {
        String barcode = request.getProductBarcode();
        Long followerId = request.getUserId();

        FollowedProduct followedProduct = followedProductRepository.findById(barcode).orElseThrow(RuntimeException::new);
        followedProduct.addFollower(String.valueOf(followerId));
        return followedProductRepository.save(followedProduct);
    }

    @Override
    public void unfollowProduct(UserFollowsProductRequest request) {
        String barcode = request.getProductBarcode();
        Long followerId = request.getUserId();

        FollowedProduct followedProduct = followedProductRepository.findById(barcode).orElseThrow(RuntimeException::new);
        followedProduct.getFollowerIds().remove(String.valueOf(followerId));

        followedProductRepository.save(followedProduct);
    }

    private boolean isPriceDecreased(double oldPrice, double newPrice) {
        return oldPrice > newPrice;
    }

    private void sendPriceDecreasedNotification(Set<String> followerIds, NotificationType notificationType,
                                                Double oldPrice, Double newPrice) {
        String title = "Hello, the product price decreased!";
        String campaignMessage = "Hello! Followed product price is decreased, take a look at it if you are still interested.";
        followerIds.forEach(followerId -> {
            NotificationMsg msg = NotificationMsg.create()
                    .to(Long.parseLong(followerId))
                    .title(title)
                    .content(campaignMessage)
                    .type(notificationType)
                    .build();

            kafkaTemplate.send(NOTIFICATION_TOPIC, JSON.toJSONString(msg));
        });
    }
}
