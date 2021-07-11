package com.gordonramsay.userfollowsproduct.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface Consumer {
    void productUpdateCallback(String message) throws JsonProcessingException;
    void productCreateCallback(String message) throws JsonProcessingException;
}
