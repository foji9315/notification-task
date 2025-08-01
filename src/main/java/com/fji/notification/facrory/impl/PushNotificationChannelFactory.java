package com.fji.notification.facrory.impl;

import com.fji.notification.facrory.ChannelListenerFactory;
import com.fji.notification.listeners.ChannelListener;
import com.fji.notification.listeners.impl.PushNotificationChannel;
import com.fji.notification.model.User;
import com.fji.notification.publisher.NotificationEventManager;

public class PushNotificationChannelFactory extends ChannelListenerFactory {

    public PushNotificationChannelFactory(NotificationEventManager notificationEventManager) {
        super(notificationEventManager);
    }

    @Override
    public ChannelListener createChannelListener(User userData) {
        return new PushNotificationChannel(userData);
    }
}
