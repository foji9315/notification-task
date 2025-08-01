package com.fji.notification.facrory.impl;

import com.fji.notification.facrory.ChannelListenerFactory;
import com.fji.notification.listeners.ChannelListener;
import com.fji.notification.listeners.impl.EmailNotificationChannel;
import com.fji.notification.model.User;
import com.fji.notification.publisher.NotificationEventManager;

public class EmailNotificationChannelFactory extends ChannelListenerFactory {

    public EmailNotificationChannelFactory(NotificationEventManager notificationEventManager) {
        super(notificationEventManager);
    }

    @Override
    public ChannelListener createChannelListener(User userData) {
        return new EmailNotificationChannel(userData);
    }
}
