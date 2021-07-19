package com.gordonramsay.userfollowsproduct.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

import java.util.HashSet;
import java.util.Set;

@Document
@Getter
@Setter
public class FollowedProduct {
    @Id
    private String barcode;

    @Field
    private String name;

    @Field
    private Double mobileSalesPrice;

    @Field
    private Double salesPrice;

    @Field
    private Set<String> followerIds = new HashSet<>();

    public void addFollower(String followerId) {
        followerIds.add(followerId);
    }
}