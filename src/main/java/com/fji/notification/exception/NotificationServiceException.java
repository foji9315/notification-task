package com.fji.notification.exception;

import lombok.Getter;
import lombok.Setter;

import java.text.MessageFormat;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Getter
@Setter
public class NotificationServiceException extends RuntimeException {

    private String errorCode;
    private String errorDetails;

    public NotificationServiceException(String errorCode, String errorDetails) {
        super(errorDetails);
        this.errorDetails = errorDetails;
        this.errorCode = errorCode;
    }

    public static NotificationServiceException buildNotificationException(String errorCode, String message, String... args) {
        if(isNotBlank(message) && args != null) {
            message = MessageFormat.format(message, args);
        }
        return new NotificationServiceException(errorCode, message);
    }
}
