package com.fji.notification.service;

import com.fji.notification.model.dto.MessageFormModel;
import com.fji.notification.service.notifiers.Notifiable;
import com.fji.notification.service.notifiers.SportNotifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class NotifierDelegator implements Notifiable {

    private final SportNotifier sportNotifier;
    private final CategoriesService categoriesService;

    @Override
    public void incomingMessage(MessageFormModel messageFormModel) {
        switch (messageFormModel.getCategory()) {
            case "Sport" -> sportNotifier.incomingMessage(messageFormModel);
            case "Finance" -> sportNotifier.incomingMessage(messageFormModel);
            case "Movies" -> sportNotifier.incomingMessage(messageFormModel);
            default -> new Exception("Unsuported Value");
        }

    }
}
