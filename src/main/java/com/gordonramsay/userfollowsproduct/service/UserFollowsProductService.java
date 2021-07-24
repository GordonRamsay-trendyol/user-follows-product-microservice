package com.gordonramsay.userfollowsproduct.service;

import com.gordonramsay.userfollowsproduct.dto.UserFollowsProductRequest;
import com.gordonramsay.userfollowsproduct.model.FollowedProduct;

import java.util.List;

public interface UserFollowsProductService {
    List<FollowedProduct> getAll();

    void updateFollowedProduct(FollowedProduct product);

    void saveFollowedProduct(FollowedProduct product);

    FollowedProduct followProduct(UserFollowsProductRequest request);

    void unfollowProduct(UserFollowsProductRequest request);
}
