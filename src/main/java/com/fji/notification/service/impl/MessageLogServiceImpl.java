package com.fji.notification.service.impl;

import com.fji.notification.model.NotificationMessage;
import com.fji.notification.model.dto.MessageFormModel;
import com.fji.notification.model.mappers.MessagesMapper;
import com.fji.notification.repository.MessageLogRepository;
import com.fji.notification.service.MessageLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageLogServiceImpl implements MessageLogService {

    private final MessageLogRepository messageLogRepository;

    @Override
    public List<MessageFormModel> getAllStoredMessages() {
        List<NotificationMessage> storedMessages = messageLogRepository.getAllNotificationMessages();

        return storedMessages.stream()
                .sorted(Comparator.comparing(NotificationMessage::getCreatedAt).reversed())
                .map(MessagesMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
