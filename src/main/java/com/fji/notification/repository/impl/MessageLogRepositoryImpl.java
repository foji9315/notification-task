package com.fji.notification.repository.impl;

import com.fji.notification.model.NotificationMessage;
import com.fji.notification.repository.MessageLogRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Optional;
import java.util.UUID;

@Repository
public class MessageLogRepositoryImpl implements MessageLogRepository {

    private static final Set<NotificationMessage> inMemoryMessages = new HashSet<>();

    @Override
    public List<NotificationMessage> getAllNotificationMessages() {
        return inMemoryMessages.stream().toList();
    }

    @Override
    public Optional<NotificationMessage> insertNewMessage(NotificationMessage message) {
        message.setId(UUID.randomUUID());
        message.setCreatedAt(LocalDateTime.now());
        boolean isMessageSaved = inMemoryMessages.add(message);
        return isMessageSaved ? Optional.of(message) : Optional.empty();
    }
}
