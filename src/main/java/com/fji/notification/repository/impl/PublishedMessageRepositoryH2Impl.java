package com.fji.notification.repository.impl;

import com.fji.notification.model.NotificationMessage;
import com.fji.notification.repository.MessageLogRepository;
import com.fji.notification.repository.rowmapper.MessageRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.fji.notification.repository.impl.PublishedMessageRepositoryH2Impl.H2_REPOSITORY;

@Repository(H2_REPOSITORY)
public class PublishedMessageRepositoryH2Impl implements MessageLogRepository {

    public static final String H2_REPOSITORY = "h2Repository";
    private static final String GET_ALL_POSTED_MESSAGES = "SELECT id, message, category, created_at FROM posted_messages";
    private static final String INSERT_NEW_POSTED_MESSAGE = "INSERT INTO posted_messages(id, message, category, created_at) VALUES (?, ?, ?, NOW())";

    private final JdbcTemplate jdbcTemplate;
    private final MessageRowMapper messageRowMapper;

    public PublishedMessageRepositoryH2Impl(JdbcTemplate jdbcTemplate,
                                            MessageRowMapper messageRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.messageRowMapper = messageRowMapper;
    }

    @Override
    public List<NotificationMessage> getAllNotificationMessages() {
        return jdbcTemplate.query(GET_ALL_POSTED_MESSAGES, messageRowMapper);
    }

    @Override
    public Optional<NotificationMessage> insertNewMessage(NotificationMessage message) {
        int rowsUpdated = jdbcTemplate.update(INSERT_NEW_POSTED_MESSAGE, statement -> {
                    statement.setString(1, UUID.randomUUID().toString());
                    statement.setString(2, message.getMessage());
                    statement.setString(3, message.getCategory().getName());
                });
        return rowsUpdated > 0 ? Optional.of(message) : Optional.empty();
    }
}
