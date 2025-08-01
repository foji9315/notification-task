package com.fji.notification.repository;

import com.fji.notification.model.NotificationMessage;

import java.util.List;
import java.util.Optional;

public interface MessageLogRepository {

    List<NotificationMessage> getAllNotificationMessages();

    Optional<NotificationMessage> insertNewMessage(NotificationMessage message);
}
