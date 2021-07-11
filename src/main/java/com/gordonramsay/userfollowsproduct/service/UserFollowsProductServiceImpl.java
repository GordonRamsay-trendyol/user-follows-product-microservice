package com.gordonramsay.userfollowsproduct.service;

import com.gordonramsay.userfollowsproduct.dto.AddUserFollowsProductRequest;
import com.gordonramsay.userfollowsproduct.model.Product;
import com.gordonramsay.userfollowsproduct.model.UserFollowsProduct;
import com.gordonramsay.userfollowsproduct.repository.ProductRepository;
import com.gordonramsay.userfollowsproduct.repository.UserFollowsProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserFollowsProductServiceImpl implements UserFollowsProductService {
    private static final String NOTIFICATION_TOPIC = "notification";

    private final ProductRepository productRepository;
    private final UserFollowsProductRepository userFollowsProductRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public UserFollowsProduct addUserFollowsProduct(AddUserFollowsProductRequest request) {
        return null;
    }

    @Override
    public UserFollowsProduct getUserFollowsProductById(Long id) {
        return null;
    }

    @Override
    public List<UserFollowsProduct> getAll() {
        return null;
    }

    @Override
    public void updateProduct(Product product) {
        UUID barcode = product.getBarcode();

        Product oldProduct = productRepository.findById(barcode).orElseThrow(RuntimeException::new);
        // TODO: Should we check null parameters, do we want to update fields as null

        Double newSalesPrice = product.getSalesPrice();
        Double newMobilePrice = product.getMobileSalesPrice();
        Double oldMobilePrice = oldProduct.getMobileSalesPrice();
        Double oldSalesPrice = oldProduct.getSalesPrice();

        boolean isMobilePriceDecreased = isPriceDecreased(oldMobilePrice, newMobilePrice);
        boolean isSalesPriceDecreased = isPriceDecreased(oldSalesPrice, newSalesPrice);

        if (isMobilePriceDecreased || isSalesPriceDecreased) {
            List<UserFollowsProduct> userFollowsProductList = userFollowsProductRepository.findByProductBarcode(barcode);
            userFollowsProductList.parallelStream().forEach(follow -> {
                // TODO: Create proper notification message
                String message = "";
                if (isMobilePriceDecreased)
                    message = "" + follow.getUserId() + oldMobilePrice + newMobilePrice;
                if (isSalesPriceDecreased)
                    message = "" + follow.getUserId() + oldSalesPrice + newSalesPrice;
                kafkaTemplate.send(NOTIFICATION_TOPIC, message);
            });
        }
        productRepository.save(product);
    }

    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    private boolean isPriceDecreased(double oldPrice, double newPrice) {
        return oldPrice > newPrice;
    }
}
