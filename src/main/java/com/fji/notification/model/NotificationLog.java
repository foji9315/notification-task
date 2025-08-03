package com.fji.notification.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationLog {

    private UUID id;
    private UUID postedMessageId;
    private UUID subscriberId;
    private ChannelEnum channelType;
    private boolean published;
    private NotificationStatus status;
    private LocalDateTime sentTimeStamp;
    private LocalDateTime receivedTimeStamp;
}
