package com.gordonramsay.userfollowsproduct.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gordonramsay.userfollowsproduct.model.Product;
import com.gordonramsay.userfollowsproduct.service.UserFollowsProductService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer implements Consumer {
    private UserFollowsProductService service;

    @Override
    @KafkaListener(topics = "product.update")
    public void productUpdateCallback(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        service.updateProduct(mapper.readValue(message, Product.class));
    }

    @Override
    @KafkaListener(topics = "product.create")
    public void productCreateCallback(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        service.saveProduct(mapper.readValue(message, Product.class));
    }
}
