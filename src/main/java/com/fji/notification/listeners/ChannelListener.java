package com.fji.notification.listeners;

import com.fji.notification.model.NotificationLog;
import com.fji.notification.model.NotificationMessage;

public interface ChannelListener {
    NotificationLog sentNotification(NotificationMessage message);
}
