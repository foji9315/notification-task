package com.fji.notification.listeners.impl;

import com.fji.notification.listeners.BaseChannelListenerTest;
import com.fji.notification.model.ChannelEnum;
import org.junit.jupiter.api.Test;

import static com.fji.notification.utils.TestConstants.MOCK_USER;

class EmailNotificationChannelTest extends BaseChannelListenerTest {

    private final EmailNotificationChannel emailNotificationChannel = new EmailNotificationChannel(MOCK_USER);

    EmailNotificationChannelTest() {
        super(ChannelEnum.EMAIL);
    }

    @Test
    public void sentNotificationSuccessTest() {
        sentNotificationSuccessTest(emailNotificationChannel);
    }

    @Test
    public void sentNotificationExceptionTest() {
        sentNotificationExceptionTest(emailNotificationChannel);
    }
}