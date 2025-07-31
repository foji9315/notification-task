package com.fji.notification.service.notifiers;

import com.fji.notification.model.dto.MessageFormModel;

@FunctionalInterface
public interface Notifiable {
    void incomingMessage(MessageFormModel messageFormModel);
}
