package com.fji.notification.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class NotificationMessage {

    private UUID id;
    private String message;
    private CategoryEnum category;
    private LocalDateTime createdAt;
}
