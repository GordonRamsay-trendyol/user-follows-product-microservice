package com.gordonramsay.userfollowsproduct.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class AddUserFollowsProductRequest {
    @NotBlank(message = "product barcode can't be blank")
    private String productBarcode;

    @NotNull(message = "user id can't be null")
    private Long userId;
}
