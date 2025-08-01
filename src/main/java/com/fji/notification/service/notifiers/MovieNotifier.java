package com.fji.notification.service.notifiers;

import com.fji.notification.model.NotificationMessage;
import com.fji.notification.publisher.NotificationEventManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import static com.fji.notification.configuration.NotificationEventConfiguration.MOVIE_EVENT_MANAGER;

@Service
public class MovieNotifier implements Notifiable {

    private final NotificationEventManager movieNotificationEventManager;

    public MovieNotifier(@Qualifier(MOVIE_EVENT_MANAGER) NotificationEventManager movieNotificationEventManager) {
        this.movieNotificationEventManager = movieNotificationEventManager;
    }

    @Override
    public void notifyIncomingMessage(NotificationMessage notificationMessage) {
        movieNotificationEventManager.notify(notificationMessage);
    }
}
