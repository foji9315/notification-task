package com.fji.notification.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotificationServiceExceptionCode {
    INVALID_VALUE("NTF-001", "Value ''{0}'' provided for field ''{1}'' is invalid."),
    INVALID_LENGTH("NTF-002", "Length of ''{0}'' exceed allowed size. Current is {1}, allowed {2}");

    private final String errorCode;
    private final String message;
}
