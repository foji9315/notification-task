package com.fji.notification.factory.impl;

import com.fji.notification.factory.ChannelListenerFactory;
import com.fji.notification.listeners.ChannelListener;
import com.fji.notification.listeners.impl.SMSNotificationChannel;
import com.fji.notification.model.User;
import com.fji.notification.publisher.NotificationEventManager;

public class SMSNotificationChannelFactory extends ChannelListenerFactory {
    public SMSNotificationChannelFactory(NotificationEventManager notificationEventManager) {
        super(notificationEventManager);
    }

    @Override
    public ChannelListener createChannelListener(User userData) {
        return new SMSNotificationChannel(userData);
    }
}
