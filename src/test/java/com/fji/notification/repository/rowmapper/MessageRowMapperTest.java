package com.fji.notification.repository.rowmapper;

import com.fji.notification.model.NotificationMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.sql.ResultSet;
import java.sql.Timestamp;

import static com.fji.notification.model.CategoryEnum.SPORT;
import static com.fji.notification.model.CategoryEnum.UNKNOW;
import static com.fji.notification.repository.rowmapper.MessageRowMapper.CATEGORY_COLUMN_NAME;
import static com.fji.notification.repository.rowmapper.MessageRowMapper.CREATED_AT_COLUMN_NAME;
import static com.fji.notification.repository.rowmapper.MessageRowMapper.ID_COLUMN_NAME;
import static com.fji.notification.repository.rowmapper.MessageRowMapper.MESSAGE_COLUMN_NAME;
import static com.fji.notification.utils.TestConstants.MESSAGE_MORE_THAN_200_CHARACTERS;
import static com.fji.notification.utils.TestConstants.TEST_LOCAL_DATE_TIME;
import static com.fji.notification.utils.TestConstants.TEST_TIME_STAMP_STRING;
import static com.fji.notification.utils.TestConstants.TEST_UUID;
import static com.fji.notification.utils.TestConstants.TEST_UUID_STRING;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MessageRowMapperTest {

    private final MessageRowMapper messageRowMapper = new MessageRowMapper();
    private final ResultSet rs = mock(ResultSet.class);

    @Test
    void mapRowTest() throws Exception {
        Timestamp timestamp = Timestamp.valueOf(TEST_TIME_STAMP_STRING);
        when(rs.getString(ID_COLUMN_NAME)).thenReturn(TEST_UUID_STRING);
        when(rs.getString(MESSAGE_COLUMN_NAME)).thenReturn(MESSAGE_MORE_THAN_200_CHARACTERS);
        when(rs.getString(CATEGORY_COLUMN_NAME)).thenReturn(SPORT.getName());
        when(rs.getTimestamp(CREATED_AT_COLUMN_NAME)).thenReturn(timestamp);

        NotificationMessage result = messageRowMapper.mapRow(rs, 0);
        assertNotNull(result, "Messages must not be null.");
        assertEquals(TEST_UUID, result.getId());
        assertEquals(MESSAGE_MORE_THAN_200_CHARACTERS, result.getMessage());
        assertEquals(SPORT, result.getCategory());
        assertEquals(TEST_LOCAL_DATE_TIME, result.getCreatedAt());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {EMPTY, "wrong"})
    void mapRowEdgeCaseTest(String categoryName) throws Exception {
        Timestamp timestamp = Timestamp.valueOf(TEST_TIME_STAMP_STRING);
        when(rs.getString(ID_COLUMN_NAME)).thenReturn(TEST_UUID_STRING);
        when(rs.getString(MESSAGE_COLUMN_NAME)).thenReturn(MESSAGE_MORE_THAN_200_CHARACTERS);
        when(rs.getString(CATEGORY_COLUMN_NAME)).thenReturn(categoryName);
        when(rs.getTimestamp(CREATED_AT_COLUMN_NAME)).thenReturn(timestamp);

        NotificationMessage result = messageRowMapper.mapRow(rs, 0);
        assertNotNull(result, "Messages must not be null.");
        assertEquals(TEST_UUID, result.getId());
        assertEquals(MESSAGE_MORE_THAN_200_CHARACTERS, result.getMessage());
        assertEquals(UNKNOW, result.getCategory());
        assertEquals(TEST_LOCAL_DATE_TIME, result.getCreatedAt());
    }
}