package com.fji.notification.service;

import com.fji.notification.model.dto.MessageFormModel;
import com.fji.notification.model.dto.UserShowMessageModel;

import java.util.List;

public interface MessageLogService {

    List<UserShowMessageModel> getAllStoredMessages();

    boolean processIncomingMessage(MessageFormModel messageFormModel);
}
