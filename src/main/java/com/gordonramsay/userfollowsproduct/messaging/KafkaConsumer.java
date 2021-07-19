package com.gordonramsay.userfollowsproduct.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gordonramsay.userfollowsproduct.model.FollowedProduct;
import com.gordonramsay.userfollowsproduct.service.UserFollowsProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaConsumer implements Consumer {
    private final UserFollowsProductService service;

    @Override
    @KafkaListener(groupId = "test", topics = "product.update")
    public void productUpdateCallback(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        service.updateFollowedProduct(mapper.readValue(message, FollowedProduct.class));
    }

    @Override
    @KafkaListener(groupId = "test", topics = "product.create")
    public void productCreateCallback(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        service.saveFollowedProduct(mapper.readValue(message, FollowedProduct.class));
    }
}
