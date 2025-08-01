package com.fji.notification.model.mappers;

import com.fji.notification.model.NotificationMessage;
import com.fji.notification.model.dto.UserShowMessageModel;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessagesMapper {

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd  HH:mm:ss");

    public static UserShowMessageModel mapToDto(NotificationMessage notificationMessage) {
        return UserShowMessageModel.builder()
                .message(notificationMessage.getMessage())
                .category(notificationMessage.getCategory().getName())
                .createdAt(notificationMessage.getCreatedAt().format(formatter))
                .build();
    }
}
