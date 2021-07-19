package com.gordonramsay.userfollowsproduct.service;

import com.gordonramsay.userfollowsproduct.dto.AddUserFollowsProductRequest;
import com.gordonramsay.userfollowsproduct.model.FollowedProduct;
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
            sendPriceDecreasedNotification(followerIds, NotificationType.MOBILE, oldMobilePrice, newMobilePrice);
        }

        if (isSalesPriceDecreased) {
            sendPriceDecreasedNotification(followerIds, NotificationType.WEB, oldSalesPrice, newSalesPrice);
        }

        // Update the product with the updated product information
        followedProductRepository.save(product);
    }

    @Override
    public void saveFollowedProduct(FollowedProduct product) {
        followedProductRepository.save(product);
    }

    @Override
    public FollowedProduct followProduct(AddUserFollowsProductRequest request) {
        String barcode = request.getProductBarcode();
        Long followerId = request.getUserId();

        FollowedProduct followedProduct = followedProductRepository.findById(barcode).orElseThrow(RuntimeException::new);
        followedProduct.addFollower(String.valueOf(followerId));
        // TODO: We shouldn't return the whole object because it contains other users ids.
        return followedProductRepository.save(followedProduct);
    }

    private boolean isPriceDecreased(double oldPrice, double newPrice) {
        return oldPrice > newPrice;
    }

    private void sendPriceDecreasedNotification(Set<String> followerIds, NotificationType notificationType,
                                                Double oldPrice, Double newPrice) {
        String campaignMessage = "Hello! Followed products price decreased, take a look if you are still interested";
        followerIds.forEach(followerId -> {
            // TODO: Build message here for a user...
            kafkaTemplate.send(NOTIFICATION_TOPIC, campaignMessage);
        });
    }
}
