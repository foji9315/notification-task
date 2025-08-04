package com.fji.notification.repository.rowmapper;

import com.fji.notification.model.ChannelEnum;
import com.fji.notification.model.NotificationLog;
import com.fji.notification.model.NotificationStatus;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

import static com.fji.notification.util.DateTimeUtils.safeConverterLocalDateTime;

@Component
public class NotificationLogRowMapper implements RowMapper<NotificationLog> {

    public static final String ID_COLUMN = "id";
    public static final String MESSAGE_ID_COLUMN = "message_id";
    public static final String SUBSCRIBER_ID_COLUMN = "subscriber_id";
    public static final String CHANNEL_TYPE_COLUMN = "channel_type";
    public static final String PUBLISHED_COLUMN = "published";
    public static final String STATUS_COLUMN = "status";
    public static final String SEND_DATE_COLUMN = "send_date";
    public static final String RECEIVED_DATE_COLUMN = "subscriber_received_date";

    @Override
    public NotificationLog mapRow(ResultSet rs, int rowNum) throws SQLException {
        return NotificationLog.builder()
                .id(UUID.fromString(rs.getString(ID_COLUMN)))
                .postedMessageId(UUID.fromString(rs.getString(MESSAGE_ID_COLUMN)))
                .subscriberId(UUID.fromString(rs.getString(SUBSCRIBER_ID_COLUMN)))
                .channelType(ChannelEnum.valueOf(rs.getString(CHANNEL_TYPE_COLUMN)))
                .published(rs.getBoolean(PUBLISHED_COLUMN))
                .status(
                        Optional.ofNullable(rs.getString(STATUS_COLUMN))
                                .map(NotificationStatus::valueOf)
                                .orElse(NotificationStatus.PENDING)
                )
                .sentTimeStamp(safeConverterLocalDateTime(rs.getTimestamp(SEND_DATE_COLUMN)))
                .receivedTimeStamp(safeConverterLocalDateTime(rs.getTimestamp(RECEIVED_DATE_COLUMN)))
                .build();
    }
}
