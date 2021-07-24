package com.gordonramsay.userfollowsproduct.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(builderClassName = "Builder", builderMethodName = "create")
public class NotificationMsg {
    private long to;
    private String title;
    private String content;
    private NotificationType type;
}
