package com.fji.notification.service.notifiers.impl;

import com.fji.notification.model.NotificationMessage;
import com.fji.notification.publisher.NotificationEventManager;
import com.fji.notification.service.notifiers.Notifiable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import static com.fji.notification.configuration.NotificationEventConfiguration.SPORT_EVENT_MANAGER;

@Service
public class SportNotifier implements Notifiable {

    private final NotificationEventManager sportNotificationEventManager;

    public SportNotifier(@Qualifier(SPORT_EVENT_MANAGER) NotificationEventManager sportNotificationEventManager) {
        this.sportNotificationEventManager = sportNotificationEventManager;
    }

    @Override
    public void notifyIncomingMessage(NotificationMessage notificationMessage) {
        sportNotificationEventManager.notify(notificationMessage);
    }
}
