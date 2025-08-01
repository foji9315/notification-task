package com.fji.notification.service;

import com.fji.notification.model.CategoryEnum;
import com.fji.notification.model.NotificationMessage;
import com.fji.notification.repository.MessageLogRepository;
import com.fji.notification.service.notifiers.impl.FinanceNotifier;
import com.fji.notification.service.notifiers.impl.MovieNotifier;
import com.fji.notification.service.notifiers.Notifiable;
import com.fji.notification.service.notifiers.impl.SportNotifier;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.fji.notification.repository.impl.PublishedMessageRepositoryH2Impl.H2_REPOSITORY;

@Service(NotifierDelegator.NOTIFIER_DELEGATOR)
public class NotifierDelegator implements Notifiable {

    public static final String NOTIFIER_DELEGATOR = "notifierDelegator";
    private final Map<CategoryEnum, Notifiable> notifiers;
    private final MessageLogRepository messageLogRepository;

    public NotifierDelegator(SportNotifier sportNotifier,
                             FinanceNotifier financeNotifier,
                             MovieNotifier movieNotifier,
                             @Qualifier(H2_REPOSITORY) MessageLogRepository messageLogRepository) {
        this.notifiers = Map.of(
                CategoryEnum.SPORT, sportNotifier,
                CategoryEnum.FINANCE, financeNotifier,
                CategoryEnum.MOVIES, movieNotifier
        );
        this.messageLogRepository = messageLogRepository;
    }

    @Override
    public void notifyIncomingMessage(NotificationMessage notificationMessage) {

        CategoryEnum channelToNotifyListeners = messageLogRepository.insertNewMessage(notificationMessage)
                .map(NotificationMessage::getCategory)
                .orElseThrow();

        notifiers.get(channelToNotifyListeners).notifyIncomingMessage(notificationMessage);
    }
}