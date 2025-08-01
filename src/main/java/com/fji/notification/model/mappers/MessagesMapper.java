package com.fji.notification.model.mappers;

import com.fji.notification.model.NotificationMessage;
import com.fji.notification.model.dto.MessageFormModel;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessagesMapper {

    public static MessageFormModel mapToDto(NotificationMessage notificationMessage) {
        return MessageFormModel.builder()
                .message(notificationMessage.getMessage())
                .category(notificationMessage.getCategory().getName())
                .createdAt(notificationMessage.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME))
                .build();
    }
}
