package com.fji.notification.validation;

import com.fji.notification.exception.NotificationServiceException;
import com.fji.notification.model.CategoryEnum;
import com.fji.notification.model.dto.MessageFormModel;
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

    private static final String MESSAGE_MORE_THAN_200_CHARACTERS = "Millions of motorists have been denied a path to claim compensation for hidden commissions paid on car loans following a Supreme Court ruling. The UK's highest court sided with finance companies in two out of three crucial test cases focusing on commission payments made by banks and other credit providers to car dealers";
    private static final String MESSAGE_WITH_200_CHARACTERS = "Millions of motorists have been denied a path to claim compensation for hidden commissions paid on car loans following a Supreme Court ruling. The UK's highest court sided with finance companies in tw";

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
                    MESSAGE_WITH_200_CHARACTERS
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
    @ValueSource(strings = {"", " ", MESSAGE_MORE_THAN_200_CHARACTERS})
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