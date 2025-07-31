package com.fji.notification.listeners.impl;

import com.fji.notification.listeners.ChannelListener;
import com.fji.notification.model.NotificationMessage;
import com.fji.notification.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class EmailNotificationChannel implements ChannelListener {

    private User userData;

    @Override
    public void sentNotification(NotificationMessage message) {
        log.info("Email to " + userData.getEmail() + ": Someone has publish a message with category " + message.getCategory() + ", where body is " + message.getMessage());
    }
}
