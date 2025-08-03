package com.fji.notification.repository;

import com.fji.notification.model.NotificationLog;

import java.util.List;

public interface NotificationLogRepository {

    List<NotificationLog> getAllNotificationLogs();

    int insertNotificationLog(NotificationLog notificationLog);
}
