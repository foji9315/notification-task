package com.fji.notification.service.impl;

import com.fji.notification.model.CategoryEnum;
import com.fji.notification.model.NotificationMessage;
import com.fji.notification.model.dto.MessageFormModel;
import com.fji.notification.model.dto.UserShowMessageModel;
import com.fji.notification.model.mappers.MessagesMapper;
import com.fji.notification.repository.MessageLogRepository;
import com.fji.notification.service.MessageLogService;
import com.fji.notification.service.notifiers.Notifiable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.fji.notification.repository.impl.PublishedMessageRepositoryH2Impl.H2_REPOSITORY;
import static com.fji.notification.service.NotifierDelegator.NOTIFIER_DELEGATOR;

@Slf4j
@Service
public class MessageLogServiceImpl implements MessageLogService {

    private final MessageLogRepository messageLogRepository;
    private final Notifiable notifierDelegatorService;

    public MessageLogServiceImpl(@Qualifier(NOTIFIER_DELEGATOR) Notifiable notifierDelegatorService,
                                 @Qualifier(H2_REPOSITORY) MessageLogRepository messageLogRepository) {
        this.notifierDelegatorService = notifierDelegatorService;
        this.messageLogRepository = messageLogRepository;
    }

    @Override
    public List<UserShowMessageModel> getAllStoredMessages() {
        List<NotificationMessage> storedMessages = messageLogRepository.getAllNotificationMessages();

        return storedMessages.stream()
                .sorted(Comparator.comparing(NotificationMessage::getCreatedAt).reversed())
                .map(MessagesMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean processIncomingMessage(MessageFormModel messageFormModel) {
        UUID newId = UUID.randomUUID();
        NotificationMessage newMessageToNotify = NotificationMessage.builder()
                .id(newId)
                .message(messageFormModel.getMessage())
                .category(CategoryEnum.valueOf(messageFormModel.getCategory()))
                .build();
        Optional<NotificationMessage> maybeNotificationMessage = messageLogRepository.insertNewMessage(newMessageToNotify);
        maybeNotificationMessage.ifPresent(notifierDelegatorService::notifyIncomingMessage);
        return maybeNotificationMessage.isPresent();
    }
}
