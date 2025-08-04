package com.fji.notification.service.impl;

import com.fji.notification.model.CategoryEnum;
import com.fji.notification.model.NotificationMessage;
import com.fji.notification.model.dto.MessageFormModel;
import com.fji.notification.model.dto.UserShowMessageModel;
import com.fji.notification.repository.MessageLogRepository;
import com.fji.notification.service.notifiers.Notifiable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Optional;

import static com.fji.notification.utils.TestConstants.MESSAGE_WITH_200_CHARACTERS;
import static com.fji.notification.utils.TestConstants.TEST_LOCAL_DATE_TIME;
import static com.fji.notification.utils.TestConstants.TEST_TIME_STAMP_STRING;
import static com.fji.notification.utils.TestConstants.TEST_UUID;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class MessageLogServiceImplTest {

    private final MessageLogRepository mockMessageLogRepository = mock(MessageLogRepository.class);
    private final Notifiable mockNotifierDelegator= mock(Notifiable.class);

    private MessageLogServiceImpl messageLogService;

    @BeforeEach
    void setup() {
        messageLogService = new MessageLogServiceImpl(mockNotifierDelegator, mockMessageLogRepository);
    }

    @Test
    void getAllStoredMessagesTest() {
        NotificationMessage notificationMessage =
                NotificationMessage.builder()
                        .id(TEST_UUID)
                        .category(CategoryEnum.SPORT)
                        .message(MESSAGE_WITH_200_CHARACTERS)
                        .createdAt(TEST_LOCAL_DATE_TIME)
                        .build();

        when(mockMessageLogRepository.getAllNotificationMessages()).thenReturn(singletonList(notificationMessage));

        List<UserShowMessageModel> messageModelList = messageLogService.getAllStoredMessages();
        assertEquals(1, messageModelList.size());
        UserShowMessageModel userShowMessageModel = messageModelList.get(0);

        assertEquals(CategoryEnum.SPORT.getName(), userShowMessageModel.getCategory());
        assertEquals(MESSAGE_WITH_200_CHARACTERS, userShowMessageModel.getMessage());
        assertEquals(TEST_TIME_STAMP_STRING, userShowMessageModel.getCreatedAt());
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void processIncomingMessageTest(boolean messageStoredCorrectly) {
        MessageFormModel messageFormModel =
                MessageFormModel.builder()
                        .category(CategoryEnum.SPORT.getName())
                        .message(MESSAGE_WITH_200_CHARACTERS)
                        .build();
        NotificationMessage notificationMessage =
                NotificationMessage.builder()
                        .id(TEST_UUID)
                        .category(CategoryEnum.SPORT)
                        .message(MESSAGE_WITH_200_CHARACTERS)
                        .build();
        if(messageStoredCorrectly) {
            when(mockMessageLogRepository.insertNewMessage(any())).thenReturn(Optional.of(notificationMessage));
            doNothing().when(mockNotifierDelegator).notifyIncomingMessage(notificationMessage);
        } else {
            when(mockMessageLogRepository.insertNewMessage(any())).thenReturn(Optional.empty());
        }

        boolean isMessageProcessed = messageLogService.processIncomingMessage(messageFormModel);
        assertEquals(messageStoredCorrectly, isMessageProcessed);
        verify(mockMessageLogRepository).insertNewMessage(any());
        if(messageStoredCorrectly)
            verify(mockNotifierDelegator).notifyIncomingMessage(any());
        else
            verifyNoInteractions(mockNotifierDelegator);
    }
}