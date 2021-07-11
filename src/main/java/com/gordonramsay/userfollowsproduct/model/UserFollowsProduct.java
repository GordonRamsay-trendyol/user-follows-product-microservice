package com.gordonramsay.userfollowsproduct.model;

import org.apache.kafka.common.protocol.types.Field;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class UserFollowsProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private UUID productBarcode;

    public UserFollowsProduct() {

    }

    public UserFollowsProduct(Long userId, UUID productBarcode) {
        this.userId = userId;
        this.productBarcode = productBarcode;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public UUID getProductBarcode() {
        return productBarcode;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setProductBarcode(UUID productBarcode) {
        this.productBarcode = productBarcode;
    }

    @Override
    public String toString() {
        return "UserFollowsProduct{" +
                "id=" + id +
                ", userId=" + userId +
                ", productBarcode=" + productBarcode +
                '}';
    }
}
