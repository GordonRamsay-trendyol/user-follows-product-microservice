package com.gordonramsay.userfollowsproduct.service;

import com.gordonramsay.userfollowsproduct.dto.AddUserFollowsProductRequest;
import com.gordonramsay.userfollowsproduct.model.Product;
import com.gordonramsay.userfollowsproduct.model.UserFollowsProduct;

import java.util.List;

public interface UserFollowsProductService {
    UserFollowsProduct addUserFollowsProduct(AddUserFollowsProductRequest request);

    UserFollowsProduct getUserFollowsProductById(Long id);

    List<UserFollowsProduct> getAll();

    void updateProduct(Product product);

    void saveProduct(Product product);
}
