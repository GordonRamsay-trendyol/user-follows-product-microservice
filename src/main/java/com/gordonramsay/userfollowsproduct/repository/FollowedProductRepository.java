package com.gordonramsay.userfollowsproduct.repository;

import com.gordonramsay.userfollowsproduct.model.FollowedProduct;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowedProductRepository extends CrudRepository<FollowedProduct, String> {
}
