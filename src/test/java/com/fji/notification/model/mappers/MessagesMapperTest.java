package com.fji.notification.model.mappers;

import com.fji.notification.model.CategoryEnum;
import com.fji.notification.model.NotificationMessage;
import com.fji.notification.model.dto.UserShowMessageModel;
import org.junit.jupiter.api.Test;

import static com.fji.notification.utils.TestConstants.MESSAGE_WITH_200_CHARACTERS;
import static com.fji.notification.utils.TestConstants.TEST_LOCAL_DATE_TIME;
import static com.fji.notification.utils.TestConstants.TEST_TIME_STAMP_STRING;
import static com.fji.notification.utils.TestConstants.TEST_UUID;
import static org.junit.jupiter.api.Assertions.*;

class MessagesMapperTest {

    @Test
    void mapNotificationMessageToUIModelTest() {
        NotificationMessage notificationMessage =
                NotificationMessage.builder()
                        .id(TEST_UUID)
                        .category(CategoryEnum.SPORT)
                        .message(MESSAGE_WITH_200_CHARACTERS)
                        .createdAt(TEST_LOCAL_DATE_TIME)
                        .build();

        UserShowMessageModel userShowMessageModel = MessagesMapper.mapToDto(notificationMessage);
        assertEquals(CategoryEnum.SPORT.getName(), userShowMessageModel.getCategory());
        assertEquals(MESSAGE_WITH_200_CHARACTERS, userShowMessageModel.getMessage());
        assertEquals(TEST_TIME_STAMP_STRING, userShowMessageModel.getCreatedAt());
    }
}