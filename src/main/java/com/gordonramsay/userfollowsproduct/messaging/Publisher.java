package com.gordonramsay.userfollowsproduct.messaging;

public interface Publisher<T> {
    void publish(String topic, T message);
}
