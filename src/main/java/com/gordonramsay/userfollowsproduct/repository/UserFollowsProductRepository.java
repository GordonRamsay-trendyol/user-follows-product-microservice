package com.gordonramsay.userfollowsproduct.repository;

import com.gordonramsay.userfollowsproduct.model.UserFollowsProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserFollowsProductRepository extends JpaRepository<UserFollowsProduct, UUID> {
    List<UserFollowsProduct> findByProductBarcode(UUID barcode);
}
