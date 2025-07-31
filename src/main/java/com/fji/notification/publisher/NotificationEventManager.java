package com.fji.notification.publisher;

import com.fji.notification.listeners.ChannelListener;
import com.fji.notification.model.NotificationMessage;

import java.util.HashSet;
import java.util.Set;

public class NotificationEventManager {

    Set<ChannelListener> listeners = new HashSet<>();

    public NotificationEventManager() {
    }

    public void subscribe(ChannelListener listener) {
        Set<ChannelListener> users = listeners;
        users.add(listener);
    }

    public void unsubscribe(ChannelListener listener) {
        Set<ChannelListener> users = listeners;
        users.remove(listener);
    }

    public void notify(NotificationMessage notificationMessage) {
        Set<ChannelListener> users = listeners;
        for (ChannelListener listener : users) {
            listener.sentNotification(notificationMessage);
        }
    }
}
