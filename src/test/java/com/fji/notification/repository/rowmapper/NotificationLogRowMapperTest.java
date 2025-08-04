package com.fji.notification.repository.rowmapper;

import com.fji.notification.model.ChannelEnum;
import com.fji.notification.model.NotificationLog;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;

import static com.fji.notification.model.NotificationStatus.PENDING;
import static com.fji.notification.repository.rowmapper.NotificationLogRowMapper.CHANNEL_TYPE_COLUMN;
import static com.fji.notification.repository.rowmapper.NotificationLogRowMapper.ID_COLUMN;
import static com.fji.notification.repository.rowmapper.NotificationLogRowMapper.MESSAGE_ID_COLUMN;
import static com.fji.notification.repository.rowmapper.NotificationLogRowMapper.PUBLISHED_COLUMN;
import static com.fji.notification.repository.rowmapper.NotificationLogRowMapper.RECEIVED_DATE_COLUMN;
import static com.fji.notification.repository.rowmapper.NotificationLogRowMapper.SEND_DATE_COLUMN;
import static com.fji.notification.repository.rowmapper.NotificationLogRowMapper.STATUS_COLUMN;
import static com.fji.notification.repository.rowmapper.NotificationLogRowMapper.SUBSCRIBER_ID_COLUMN;
import static com.fji.notification.utils.TestConstants.TEST_UUID;
import static com.fji.notification.utils.TestConstants.TEST_UUID_STRING;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NotificationLogRowMapperTest {

    private final NotificationLogRowMapper notificationLogRowMapper = new NotificationLogRowMapper();

    private final ResultSet rs = mock(ResultSet.class);

    @Test
    void mapRowTest() throws Exception {
        when(rs.getString(ID_COLUMN)).thenReturn(TEST_UUID_STRING);
        when(rs.getString(MESSAGE_ID_COLUMN)).thenReturn(TEST_UUID_STRING);
        when(rs.getString(SUBSCRIBER_ID_COLUMN)).thenReturn(TEST_UUID_STRING);
        when(rs.getString(CHANNEL_TYPE_COLUMN)).thenReturn(ChannelEnum.EMAIL.name());
        when(rs.getBoolean(PUBLISHED_COLUMN)).thenReturn(true);
        when(rs.getString(STATUS_COLUMN)).thenReturn(null);
        when(rs.getString(SEND_DATE_COLUMN)).thenReturn(null);
        when(rs.getString(RECEIVED_DATE_COLUMN)).thenReturn(null);

        NotificationLog notificationLogResult = notificationLogRowMapper.mapRow(rs, 0);

        assertNotNull(notificationLogResult);
        assertEquals(TEST_UUID, notificationLogResult.getId());
        assertEquals(TEST_UUID, notificationLogResult.getPostedMessageId());
        assertEquals(TEST_UUID, notificationLogResult.getSubscriberId());
        assertEquals(ChannelEnum.EMAIL, notificationLogResult.getChannelType());
        assertTrue(notificationLogResult.isPublished());
        assertEquals(PENDING, notificationLogResult.getStatus());
        assertNull(notificationLogResult.getSentTimeStamp());
        assertNull(notificationLogResult.getReceivedTimeStamp());
    }
}