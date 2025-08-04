package com.fji.notification.util;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static com.fji.notification.util.DateTimeUtils.getSqlTimestamp;
import static com.fji.notification.util.DateTimeUtils.safeConverterLocalDateTime;
import static com.fji.notification.utils.TestConstants.TEST_LOCAL_DATE_TIME;
import static com.fji.notification.utils.TestConstants.TEST_TIME_STAMP;
import static org.junit.jupiter.api.Assertions.*;

class DateTimeUtilsTest {

    @Test
    void safeConverterLocalDateTimeTest() {
        LocalDateTime localDateTime = safeConverterLocalDateTime(TEST_TIME_STAMP);
        assertEquals(TEST_LOCAL_DATE_TIME, localDateTime);
    }

    @Test
    void getSqlTimestampTest() {
        Timestamp timestamp = getSqlTimestamp(TEST_LOCAL_DATE_TIME);
        assertEquals(TEST_TIME_STAMP, timestamp);
    }
}