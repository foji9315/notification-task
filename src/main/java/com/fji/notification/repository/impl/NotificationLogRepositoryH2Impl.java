package com.fji.notification.repository.impl;

import com.fji.notification.model.NotificationLog;
import com.fji.notification.repository.NotificationLogRepository;
import com.fji.notification.repository.rowmapper.NotificationLogRowMapper;
import jakarta.transaction.Transactional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.fji.notification.util.DateTimeUtils.getSqlTimestamp;

@Repository(NotificationLogRepositoryH2Impl.H2_NOTIFICATION_LOG_REPOSITORY)
public class NotificationLogRepositoryH2Impl implements NotificationLogRepository {

    public static final String H2_NOTIFICATION_LOG_REPOSITORY = "h2Repository";

    private static final int MAX_BATCH_SIZE = 100;
    private static final String INSERT_SENT_NOTIFICATION_LOG_ENTRY = "INSERT INTO notification_log (id, message_id, subscriber_id, channel_type, published, status, send_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_ALL_NOTIFICATION_LOGS = "SELECT id, message_id, subscriber_id, channel_type, published, status, send_date, subscriber_received_date FROM notification_log";

    private final JdbcTemplate jdbcTemplate;
    private final NotificationLogRowMapper notificationLogRowMapper;

    public NotificationLogRepositoryH2Impl(JdbcTemplate jdbcTemplate, NotificationLogRowMapper notificationLogRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.notificationLogRowMapper = notificationLogRowMapper;
    }

    @Override
    public List<NotificationLog> getAllNotificationLogs() {
        return jdbcTemplate.query(GET_ALL_NOTIFICATION_LOGS, notificationLogRowMapper);
    }

    @Override
    public int insertNotificationLog(NotificationLog notificationLog) {
        return jdbcTemplate.update(INSERT_SENT_NOTIFICATION_LOG_ENTRY, statement -> {
            statement.setString(1, UUID.randomUUID().toString());
            statement.setString(2, notificationLog.getPostedMessageId().toString());
            statement.setString(3, notificationLog.getSubscriberId().toString());
            statement.setString(4, notificationLog.getChannelType().getChannel());
            statement.setBoolean(5, notificationLog.isPublished());
            statement.setString(6, notificationLog.getStatus().getValue());
            statement.setTimestamp(7, getSqlTimestamp(notificationLog.getSentTimeStamp()));

        });
    }

    @Override
    public int insertBatchOfNotificationLog(List<NotificationLog> notificationLogs) {
        int[][] updateCounts = jdbcTemplate.batchUpdate(
                INSERT_SENT_NOTIFICATION_LOG_ENTRY,
                notificationLogs,
                MAX_BATCH_SIZE,
                (statement, notificationLog) -> {
                    statement.setString(1, UUID.randomUUID().toString());
                    statement.setString(2, notificationLog.getPostedMessageId().toString());
                    statement.setString(3, notificationLog.getSubscriberId().toString());
                    statement.setString(4, notificationLog.getChannelType().getChannel());
                    statement.setBoolean(5, notificationLog.isPublished());
                    statement.setString(6, notificationLog.getStatus().getValue());
                    statement.setTimestamp(7, getSqlTimestamp(notificationLog.getSentTimeStamp()));
                }
        );
        return Arrays.stream(updateCounts).flatMapToInt(Arrays::stream).sum();
    }
}
