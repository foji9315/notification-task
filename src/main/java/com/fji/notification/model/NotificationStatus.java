package com.fji.notification.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotificationStatus {
    PENDING("PENDING"),
    SENT("SENT"),
    RECEIVED("RECEIVED"),
    READ("READ");

    private final String value;
}
