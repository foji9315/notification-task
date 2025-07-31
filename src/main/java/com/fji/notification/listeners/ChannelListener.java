package com.fji.notification.listeners;

import com.fji.notification.model.NotificationMessage;

public interface ChannelListener {
    void sentNotification(NotificationMessage message);
}
