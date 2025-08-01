package com.fji.notification.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageFormModel {

    private String message;
    private String category;
    private String createdAt;
}

