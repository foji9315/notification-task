package com.fji.notification.factory;

import com.fji.notification.listeners.ChannelListener;
import com.fji.notification.model.User;
import com.fji.notification.publisher.NotificationEventManager;

public abstract class ChannelListenerFactory {

    private final NotificationEventManager notificationEventManager;

    protected ChannelListenerFactory(NotificationEventManager notificationEventManager) {
        this.notificationEventManager = notificationEventManager;
    }

    public void subscribe(User userData) {
        ChannelListener changeListener = createChannelListener(userData);
        notificationEventManager.subscribe(changeListener);
    }

    public abstract ChannelListener createChannelListener(User userData);
}
