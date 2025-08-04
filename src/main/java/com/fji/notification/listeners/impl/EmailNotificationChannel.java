package com.fji.notification.listeners.impl;

import com.fji.notification.listeners.ChannelListener;
import com.fji.notification.model.ChannelEnum;
import com.fji.notification.model.NotificationLog;
import com.fji.notification.model.NotificationMessage;
import com.fji.notification.model.NotificationStatus;
import com.fji.notification.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
public class EmailNotificationChannel implements ChannelListener {

    private User userData;

    @Override
    public NotificationLog sentNotification(NotificationMessage message) {
        NotificationLog notificationLog = NotificationLog.builder()
                .id(UUID.randomUUID())
                .postedMessageId(message.getId())
                .subscriberId(UUID.fromString(userData.getId()))
                .build();
        notificationLog.setChannelType(ChannelEnum.EMAIL);
        try {
            log.info("Email to {}: Someone has publish a message with category {}, where body is {}", userData.getEmail(), message.getCategory(), message.getMessage());
            notificationLog.setPublished(true);
            notificationLog.setStatus(NotificationStatus.SENT);
            if("EXCEPTION_TEST".equalsIgnoreCase(message.getMessage())) {
                throw new RuntimeException("Test impl exception");
            } else {
                notificationLog.setSentTimeStamp(LocalDateTime.now());
            }
        } catch (Exception exception) {
            notificationLog.setPublished(false);
            notificationLog.setStatus(NotificationStatus.PENDING);
            log.error("Error while sending Email to {} for messageId : {}. NotificationLog created for retry later", userData.getEmail(), message.getId());
        }
        return notificationLog;
    }
}
