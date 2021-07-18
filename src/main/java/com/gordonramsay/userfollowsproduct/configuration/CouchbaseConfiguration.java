package com.gordonramsay.userfollowsproduct.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;

@Configuration
public class CouchbaseConfiguration extends AbstractCouchbaseConfiguration {
    private static final String CONNECTION_STRING = "172.17.0.2";
    private static final String USERNAME = "myapp";
    private static final String PASSWORD = "123321";
    private static final String BUCKET_NAME = "followed_products";

    @Override
    public String getConnectionString() {
        return CONNECTION_STRING;
    }

    @Override
    public String getUserName() {
        return USERNAME;
    }

    @Override
    public String getPassword() {
        return PASSWORD;
    }

    @Override
    public String getBucketName() {
        return BUCKET_NAME;
    }
}
