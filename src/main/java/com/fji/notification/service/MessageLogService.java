package com.fji.notification.service;

import com.fji.notification.model.dto.MessageFormModel;

import java.util.List;

public interface MessageLogService {

    List<MessageFormModel> getAllStoredMessages();
}
