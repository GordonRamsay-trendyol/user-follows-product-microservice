package com.gordonramsay.userfollowsproduct.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AddUserFollowsProductRequest {
    @NotNull(message = "product can't be null")
    @NotBlank(message = "product can't be blank")
    private String productBarcode;

    @NotNull(message = "user can't be null")
    @NotBlank(message = "user can't be blank")
    private Long userId;
}
