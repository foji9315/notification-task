package com.fji.notification.publisher;

import com.fji.notification.listeners.ChannelListener;
import com.fji.notification.model.NotificationLog;
import com.fji.notification.model.NotificationMessage;
import com.fji.notification.repository.NotificationLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.fji.notification.configuration.AsyncConfiguration.ASYNC_EXECUTOR_NOTIFIER;

@Slf4j
public class NotificationEventManager {

    private final Set<ChannelListener> listeners = new HashSet<>();

    @Autowired
    private NotificationLogRepository notificationLogRepository;

    public NotificationEventManager() {
    }

    public void subscribe(ChannelListener listener) {
        listeners.add(listener);
    }

    public void unsubscribe(ChannelListener listener) {
        listeners.remove(listener);
    }

//    @Async(ASYNC_EXECUTOR_NOTIFIER)
    public void notify(NotificationMessage notificationMessage) {
        Set<ChannelListener> users = listeners;
        log.info("Sending {} notification to {} users", notificationMessage.getCategory(), listeners.size());
        List<NotificationLog> notificationLogs = new ArrayList<>(users.size());
        for (ChannelListener listener : users) {
            NotificationLog notificationLog = listener.sentNotification(notificationMessage);
            notificationLogRepository.insertNotificationLog(notificationLog);
            notificationLogs.add(notificationLog);
        }
        log.info("NotificationLogs generated: {}", notificationLogs);
    }
}
