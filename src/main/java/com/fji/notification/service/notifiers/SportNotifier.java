package com.fji.notification.service.notifiers;

import com.fji.notification.model.NotificationMessage;
import com.fji.notification.model.dto.MessageFormModel;
import com.fji.notification.publisher.NotificationEventManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.fji.notification.configuration.NotificationEventConfiguration.SPORT_EVENT_MANAGER;

@Service
public class SportNotifier implements Notifiable {

    private final NotificationEventManager sportNotificationEventManager;

    public SportNotifier(@Qualifier(SPORT_EVENT_MANAGER) NotificationEventManager sportNotificationEventManager) {
        this.sportNotificationEventManager = sportNotificationEventManager;
    }

    @Override
    public void incomingMessage(MessageFormModel messageFormModel) {
        NotificationMessage notificationMessage = NotificationMessage.builder()
                .id(UUID.randomUUID())
                .message(messageFormModel.getMessage())
                .category(messageFormModel.getCategory())
                .build();
        sportNotificationEventManager.notify(notificationMessage);
    }
}
