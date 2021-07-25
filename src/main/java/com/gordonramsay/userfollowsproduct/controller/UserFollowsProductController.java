package com.gordonramsay.userfollowsproduct.controller;

import com.gordonramsay.userfollowsproduct.dto.UserFollowsProductRequest;
import com.gordonramsay.userfollowsproduct.model.FollowedProduct;
import com.gordonramsay.userfollowsproduct.service.UserFollowsProductService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.var;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products/{barcode}/follow")
@RequiredArgsConstructor
public class UserFollowsProductController {
    private final UserFollowsProductService service;

    //How would this fit into the barcode/follow path?
    //Is this just for testing anyways?
    @GetMapping
    public ResponseEntity<List<FollowedProduct>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Getter
    @Setter
    public static class ProductRequest {
        private String barcode;
        private String name;
        private Double mobileSalesPrice;
        private Double salesPrice;
    }

    @PostMapping("/products")
    public ResponseEntity<?> createNewProduct(@RequestBody @Valid ProductRequest request) {
        FollowedProduct followedProduct = new FollowedProduct();
        followedProduct.setBarcode(request.barcode);
        followedProduct.setName(request.name);
        followedProduct.setMobileSalesPrice(request.mobileSalesPrice);
        followedProduct.setSalesPrice(request.salesPrice);
        service.saveFollowedProduct(followedProduct);
        return ResponseEntity.ok().body("Object created!");
    }

    //id of what? Needs a better name
    @PostMapping
    public ResponseEntity<?> follow(@PathVariable String barcode, @RequestHeader Long id) {
        UserFollowsProductRequest request = new UserFollowsProductRequest();
        request.setUserId(id);
        request.setProductBarcode(barcode);

        var userFollowsProduct = service.followProduct(request);
        return ResponseEntity.ok(userFollowsProduct);
    }

    @PutMapping("/unfollow")
    public ResponseEntity<?> unfollow(@RequestBody @Valid UserFollowsProductRequest request) {
        service.unfollowProduct(request);
        return ResponseEntity.ok().build();
    }

}
