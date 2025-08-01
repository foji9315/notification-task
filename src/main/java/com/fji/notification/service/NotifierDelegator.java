package com.fji.notification.service;

import com.fji.notification.model.CategoryEnum;
import com.fji.notification.model.NotificationMessage;
import com.fji.notification.service.notifiers.impl.FinanceNotifier;
import com.fji.notification.service.notifiers.impl.MovieNotifier;
import com.fji.notification.service.notifiers.Notifiable;
import com.fji.notification.service.notifiers.impl.SportNotifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service(NotifierDelegator.NOTIFIER_DELEGATOR)
public class NotifierDelegator implements Notifiable {

    public static final String NOTIFIER_DELEGATOR = "notifierDelegator";
    private final Map<CategoryEnum, Notifiable> notifiers;

    public NotifierDelegator(SportNotifier sportNotifier,
                             FinanceNotifier financeNotifier,
                             MovieNotifier movieNotifier) {
        this.notifiers = Map.of(
                CategoryEnum.SPORT, sportNotifier,
                CategoryEnum.FINANCE, financeNotifier,
                CategoryEnum.MOVIES, movieNotifier
        );
    }

    @Override
    public void notifyIncomingMessage(NotificationMessage notificationMessage) {
        CategoryEnum channelToNotifyListeners = notificationMessage.getCategory();
        notifiers.get(channelToNotifyListeners).notifyIncomingMessage(notificationMessage);
    }
}