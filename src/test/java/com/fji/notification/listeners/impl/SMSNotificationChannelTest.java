package com.fji.notification.listeners.impl;

import com.fji.notification.listeners.BaseChannelListenerTest;
import com.fji.notification.model.ChannelEnum;
import org.junit.jupiter.api.Test;

import static com.fji.notification.utils.TestConstants.MOCK_USER;

class SMSNotificationChannelTest extends BaseChannelListenerTest {

    private final SMSNotificationChannel smsNotificationChannel = new SMSNotificationChannel(MOCK_USER);

    SMSNotificationChannelTest() {
        super(ChannelEnum.SMS);
    }

    @Test
    public void sentNotificationSuccessTest() {
        sentNotificationSuccessTest(smsNotificationChannel);
    }

    @Test
    public void sentNotificationExceptionTest() {
        sentNotificationExceptionTest(smsNotificationChannel);
    }
}