package com.fji.notification.listeners.impl;

import com.fji.notification.listeners.BaseChannelListenerTest;
import com.fji.notification.model.ChannelEnum;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.fji.notification.utils.TestConstants.MOCK_USER;

class PushNotificationChannelTest extends BaseChannelListenerTest {

    private final PushNotificationChannel pushNotificationChannel = new PushNotificationChannel(MOCK_USER);

    PushNotificationChannelTest() {
        super(ChannelEnum.PUSH);
    }

    @Test
    public void sentNotificationSuccessTest() {
        sentNotificationSuccessTest(pushNotificationChannel);
    }

    @Test
    public void sentNotificationExceptionTest() {
        sentNotificationExceptionTest(pushNotificationChannel);
    }
}