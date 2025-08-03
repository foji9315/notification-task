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
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Async(ASYNC_EXECUTOR_NOTIFIER)
    public void notify(NotificationMessage notificationMessage) {
        log.info("Sending {} notification to {} users", notificationMessage.getCategory(), listeners.size());
        List<NotificationLog> notificationLogs = new ArrayList<>(listeners.size());
        for (ChannelListener listener : listeners) {
            NotificationLog notificationLog = listener.sentNotification(notificationMessage);
            notificationLogs.add(notificationLog);
        }
        log.info("Storing NotificationLogs generated with ids : {}", notificationLogs.stream().map(NotificationLog::getId).map(UUID::toString).collect(Collectors.joining(", ")));
        int rowsInserted = notificationLogRepository.insertBatchOfNotificationLog(notificationLogs);
        log.info("Number of notificationLogs inserted {}, expected {} rows.", rowsInserted, notificationLogs.size());
        if(rowsInserted < listeners.size()) {
            log.warn("No all listeners where notified correctly, using retry strategy");
        }
    }
}
