package com.fji.notification.service.notifiers;

import com.fji.notification.model.NotificationMessage;

@FunctionalInterface
public interface Notifiable {

    void notifyIncomingMessage(NotificationMessage notificationMessage);
}
