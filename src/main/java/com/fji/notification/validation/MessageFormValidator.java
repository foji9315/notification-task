package com.fji.notification.validation;

import com.fji.notification.exception.NotificationServiceException;
import com.fji.notification.model.dto.MessageFormModel;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.fji.notification.exception.NotificationServiceExceptionCode.INVALID_LENGTH;
import static com.fji.notification.exception.NotificationServiceExceptionCode.INVALID_VALUE;
import static org.apache.commons.lang3.StringUtils.isBlank;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageFormValidator {

    public static final String CATEGORY_FIELD_NAME = "Category";
    public static final String MESSAGE_FIELD_NAME = "Message";
    public static final int ALLOWED_MESSAGE_LENGTH = 200;

    public static void validate(MessageFormModel messageFormModel) {
        validateCategory(messageFormModel.getCategory());
        validateMessageContent(messageFormModel.getMessage());
    }

    private static void validateCategory(String categoryValue) {
        if (isBlank(categoryValue) ||
                CommonValidator.isNotValidCategoryValue(categoryValue)) {
            throw NotificationServiceException.buildNotificationException(
                    INVALID_VALUE.getErrorCode(),
                    INVALID_VALUE.getMessage(),
                    categoryValue,
                    CATEGORY_FIELD_NAME);
        }
    }

    private static void validateMessageContent(String messageValue) {
        if(isBlank(messageValue)) {
            throw NotificationServiceException.buildNotificationException(
                    INVALID_VALUE.getErrorCode(),
                    INVALID_VALUE.getMessage(),
                    messageValue,
                    MESSAGE_FIELD_NAME);
        }

        if(messageValue.length() > 200 ) {
            throw NotificationServiceException.buildNotificationException(
                    INVALID_LENGTH.getErrorCode(),
                    INVALID_LENGTH.getMessage(),
                    MESSAGE_FIELD_NAME,
                    String.valueOf(messageValue.length()),
                    String.valueOf(ALLOWED_MESSAGE_LENGTH));
        }
    }

}
