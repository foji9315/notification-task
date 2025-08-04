package com.fji.notification.publisher;

import com.fji.notification.listeners.ChannelListener;
import com.fji.notification.model.CategoryEnum;
import com.fji.notification.model.ChannelEnum;
import com.fji.notification.model.NotificationLog;
import com.fji.notification.model.NotificationMessage;
import com.fji.notification.repository.NotificationLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static com.fji.notification.model.NotificationStatus.PENDING;
import static com.fji.notification.model.NotificationStatus.SENT;
import static com.fji.notification.utils.TestConstants.MOCK_USER_ID;
import static com.fji.notification.utils.TestConstants.TEST_LOCAL_DATE_TIME;
import static com.fji.notification.utils.TestConstants.TEST_NOTIFICATION_MESSAGE_ID;
import static com.fji.notification.utils.TestConstants.TEST_UUID;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationEventManagerTest {

    @Mock
    private NotificationLogRepository notificationLogRepository;

    @InjectMocks
    private NotificationEventManager notificationEventManager;

    @BeforeEach
    void setUp() {
    }

    @Test
    void subscribeAndUnsubscribeTest() throws Exception {
        // Make accessible listeners field
        Field channelListenersField = notificationEventManager.getClass().getDeclaredField("listeners");
        channelListenersField.setAccessible(true);

        ChannelListener channelListener = mock(ChannelListener.class);
        // Test subscribe
        notificationEventManager.subscribe(channelListener);
        Set<ChannelListener> listeners = (Set<ChannelListener>) channelListenersField.get(notificationEventManager);
        assertTrue(listeners.contains(channelListener));

        // Test unsubscribe
        notificationEventManager.unsubscribe(channelListener);
        listeners = (Set<ChannelListener>) channelListenersField.get(notificationEventManager);
        assertFalse(listeners.contains(channelListener));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    void testNotifyProcess(int numberOfRowsUpdated) {
        // Subscribe a Listener
        ChannelListener channelListener = mock(ChannelListener.class);
        notificationEventManager.subscribe(channelListener);

        NotificationMessage notificationMessage =
                NotificationMessage.builder()
                        .id(TEST_UUID)
                        .category(CategoryEnum.SPORT)
                        .createdAt(TEST_LOCAL_DATE_TIME)
                        .build();

        NotificationLog notificationLog =
                NotificationLog.builder()
                        .id(TEST_UUID)
                        .subscriberId(UUID.fromString(MOCK_USER_ID))
                        .postedMessageId(TEST_NOTIFICATION_MESSAGE_ID)
                        .channelType(ChannelEnum.EMAIL)
                        .published(true)
                        .status(SENT)
                        .sentTimeStamp(LocalDateTime.now())
                        .build();
        if(numberOfRowsUpdated > 0) {
            notificationLog.setPublished(true);
            notificationLog.setStatus(SENT);
            notificationLog.setSentTimeStamp(LocalDateTime.now());
        } else {
            notificationLog.setPublished(false);
            notificationLog.setStatus(PENDING);
        }
        when(channelListener.sentNotification(notificationMessage)).thenReturn(notificationLog);
        when(notificationLogRepository.insertBatchOfNotificationLog(singletonList(notificationLog))).thenReturn(numberOfRowsUpdated);
        notificationEventManager.notify(notificationMessage);
        verify(channelListener, times(1)).sentNotification(notificationMessage);
        verify(notificationLogRepository, times(1)).insertBatchOfNotificationLog(singletonList(notificationLog));
    }
}