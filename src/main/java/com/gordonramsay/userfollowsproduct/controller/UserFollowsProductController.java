package com.gordonramsay.userfollowsproduct.controller;

import com.gordonramsay.userfollowsproduct.dto.AddUserFollowsProductRequest;
import com.gordonramsay.userfollowsproduct.model.UserFollowsProduct;
import com.gordonramsay.userfollowsproduct.service.UserFollowsProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/userfollowsproduct")
@RequiredArgsConstructor
public class UserFollowsProductController {
    private final UserFollowsProductService service;

    @GetMapping
    public ResponseEntity<List<UserFollowsProduct>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping
    public ResponseEntity<?> follow(@RequestBody @Valid AddUserFollowsProductRequest request) {
        var userFollowsProduct = service.addUserFollowsProduct(request);
        return ResponseEntity.ok(userFollowsProduct);
    }
}
