package com.gordonramsay.userfollowsproduct.repository;

import com.gordonramsay.userfollowsproduct.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
