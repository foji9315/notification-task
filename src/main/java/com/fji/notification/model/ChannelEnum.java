package com.fji.notification.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChannelEnum {
    EMAIL("EMAIL"),
    PUSH("PUSH"),
    SMS("SMS");

    private final String channel;
}
