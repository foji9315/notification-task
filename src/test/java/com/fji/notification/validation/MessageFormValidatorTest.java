package com.fji.notification.validation;

import com.fji.notification.exception.NotificationServiceException;
import com.fji.notification.model.CategoryEnum;
import com.fji.notification.model.dto.MessageFormModel;
import com.fji.notification.utils.TestConstants;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.text.MessageFormat;

import static com.fji.notification.exception.NotificationServiceExceptionCode.INVALID_LENGTH;
import static com.fji.notification.exception.NotificationServiceExceptionCode.INVALID_VALUE;
import static com.fji.notification.validation.MessageFormValidator.ALLOWED_MESSAGE_LENGTH;
import static com.fji.notification.validation.MessageFormValidator.CATEGORY_FIELD_NAME;
import static com.fji.notification.validation.MessageFormValidator.MESSAGE_FIELD_NAME;
import static java.lang.String.valueOf;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.junit.jupiter.api.Assertions.*;

class MessageFormValidatorTest {

    @ParameterizedTest
    @ValueSource(strings = {"SPORT", "FINANCE", "MOVIES"})
    void validateCategoryTest(String categoryValue) {
        MessageFormModel messageToValidate =
                MessageFormModel.builder()
                        .category(categoryValue)
                        .message("Message")
                        .build();
        assertDoesNotThrow(() -> MessageFormValidator.validate(messageToValidate));
    }

    @ParameterizedTest
    @ValueSource(
            strings = {
                    "Not blank message",
                    TestConstants.MESSAGE_WITH_200_CHARACTERS
            }
    )
    void validateMessageTest(String messageValue) {
        MessageFormModel messageToValidate =
                MessageFormModel.builder()
                        .category(CategoryEnum.FINANCE.getName())
                        .message(messageValue)
                        .build();
        assertDoesNotThrow(() -> MessageFormValidator.validate(messageToValidate));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " ", "Wrong Category"})
    void validateFailsForCategoryTest(String categoryValue) {
        MessageFormModel messageToValidate =
                MessageFormModel.builder()
                        .category(categoryValue)
                        .message("Message")
                        .build();
        NotificationServiceException ex = assertThrows(NotificationServiceException.class, () -> MessageFormValidator.validate(messageToValidate));
        assertEquals(INVALID_VALUE.getErrorCode(), ex.getErrorCode(), "Error code does not math.");
        assertEquals(MessageFormat.format(INVALID_VALUE.getMessage(), categoryValue, CATEGORY_FIELD_NAME), ex.getMessage(), "Error message does not math.");
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " ", TestConstants.MESSAGE_MORE_THAN_200_CHARACTERS})
    void validateFailsForMessageTest(String messageValue) {
        MessageFormModel messageToValidate =
                MessageFormModel.builder()
                        .category(CategoryEnum.FINANCE.getName())
                        .message(messageValue)
                        .build();
        NotificationServiceException ex = assertThrows(NotificationServiceException.class, () -> MessageFormValidator.validate(messageToValidate));
        if(isNotBlank(messageValue)) {
            assertEquals(INVALID_LENGTH.getErrorCode(), ex.getErrorCode(), "Error code does not math.");
            assertEquals(MessageFormat.format(INVALID_LENGTH.getMessage(), MESSAGE_FIELD_NAME, valueOf(messageValue.length()), valueOf(ALLOWED_MESSAGE_LENGTH)), ex.getMessage(), "Error message does not math.");
        } else {
            assertEquals(INVALID_VALUE.getErrorCode(), ex.getErrorCode(), "Error code does not math.");
            assertEquals(MessageFormat.format(INVALID_VALUE.getMessage(), messageValue, MESSAGE_FIELD_NAME), ex.getMessage(), "Error message does not math.");
        }
    }
}