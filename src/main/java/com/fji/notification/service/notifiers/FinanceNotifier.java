package com.fji.notification.service.notifiers;

import com.fji.notification.model.NotificationMessage;
import com.fji.notification.publisher.NotificationEventManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import static com.fji.notification.configuration.NotificationEventConfiguration.FINANCE_EVENT_MANAGER;

@Service
public class FinanceNotifier implements Notifiable {

    private final NotificationEventManager financeNotificationEventManager;

    public FinanceNotifier(@Qualifier(FINANCE_EVENT_MANAGER) NotificationEventManager financeNotificationEventManager) {
        this.financeNotificationEventManager = financeNotificationEventManager;
    }

    @Override
    public void notifyIncomingMessage(NotificationMessage notificationMessage) {
        financeNotificationEventManager.notify(notificationMessage);
    }
}
