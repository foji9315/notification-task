package com.fji.notification.listeners;

import com.fji.notification.model.ChannelEnum;
import com.fji.notification.model.NotificationLog;
import com.fji.notification.model.NotificationStatus;
import lombok.extern.slf4j.Slf4j;

import static com.fji.notification.utils.TestConstants.MOCK_USER_ID;
import static com.fji.notification.utils.TestConstants.TEST_EXCEPTION_NOTIFICATION_MESSAGE;
import static com.fji.notification.utils.TestConstants.TEST_NOTIFICATION_MESSAGE;
import static com.fji.notification.utils.TestConstants.TEST_NOTIFICATION_MESSAGE_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public abstract class BaseChannelListenerTest {

    private final ChannelEnum expectedTargetChannel;

    protected BaseChannelListenerTest(ChannelEnum expectedTargetChannel) {
        this.expectedTargetChannel = expectedTargetChannel;
    }

    public abstract void sentNotificationSuccessTest();

    public abstract void sentNotificationExceptionTest();

    public void sentNotificationSuccessTest(ChannelListener channelListenerToTest) {
        NotificationLog notificationLog = channelListenerToTest.sentNotification(TEST_NOTIFICATION_MESSAGE);
        assertNotNull(notificationLog.getId(), "Notification must contain a new id generated");
        assertEquals(TEST_NOTIFICATION_MESSAGE_ID, notificationLog.getPostedMessageId(), "Notification message id does not match.");
        assertEquals(MOCK_USER_ID, notificationLog.getSubscriberId().toString(), "UserId does not match.");
        assertEquals(expectedTargetChannel, notificationLog.getChannelType(), "ChannelType does not match.");
        assertTrue(notificationLog.isPublished(), "Message must be published.");
        assertEquals(NotificationStatus.SENT, notificationLog.getStatus(), "Notification status does not match.");
        assertNotNull(notificationLog.getSentTimeStamp(), "Notification must have a sent timeStamp");
    }

    public void sentNotificationExceptionTest(ChannelListener channelListenerToTest) {
        /*
        tried to use mockStatic but for some reason last test throws a npe error
        NotificationLog notificationLog;
        try (MockedStatic<LocalDateTime> localDateTimeMockedStatic = mockStatic(LocalDateTime.class)) {
            localDateTimeMockedStatic.when(LocalDateTime::now).thenThrow(new Exception());
            notificationLog = channelListenerToTest.sentNotification(TEST_EXCEPTION_NOTIFICATION_MESSAGE);
        }*/
        NotificationLog notificationLog = channelListenerToTest.sentNotification(TEST_EXCEPTION_NOTIFICATION_MESSAGE);
        assertNotNull(notificationLog.getId(), "Notification must contain a new id generated");
        assertEquals(TEST_NOTIFICATION_MESSAGE_ID, notificationLog.getPostedMessageId(), "Notification message id does not match.");
        assertEquals(MOCK_USER_ID, notificationLog.getSubscriberId().toString(), "UserId does not match.");
        assertEquals(expectedTargetChannel, notificationLog.getChannelType(), "ChannelType does not match.");
        assertFalse(notificationLog.isPublished(), "Message must be published.");
        assertEquals(NotificationStatus.PENDING, notificationLog.getStatus(), "Notification status does not match.");
    }
}
