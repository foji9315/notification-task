package com.fji.notification.repository.rowmapper;

import com.fji.notification.model.CategoryEnum;
import com.fji.notification.model.NotificationMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Slf4j
@Component
public class MessageRowMapper implements RowMapper<NotificationMessage> {

    public static final String ID_COLUMN_NAME = "id";
    public static final String MESSAGE_COLUMN_NAME = "message";
    public static final String CATEGORY_COLUMN_NAME = "category";
    public static final String CREATED_AT_COLUMN_NAME = "created_at";

    @Override
    public NotificationMessage mapRow(ResultSet rs, int rowNum) throws SQLException {
        log.debug("Mapping row {}", rowNum);
        return NotificationMessage.builder()
                .id(UUID.fromString(rs.getString(ID_COLUMN_NAME)))
                .message(rs.getString(MESSAGE_COLUMN_NAME))
                .category(CategoryEnum.valueOf(rs.getString(CATEGORY_COLUMN_NAME)))
                .createdAt(rs.getTimestamp(CREATED_AT_COLUMN_NAME).toLocalDateTime())
                .build();
    }
}
