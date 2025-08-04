package com.fji.notification.repository.impl;

import com.fji.notification.model.ChannelEnum;
import com.fji.notification.model.NotificationLog;
import com.fji.notification.model.NotificationStatus;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.fji.notification.repository.impl.NotificationLogRepositoryH2Impl.H2_NOTIFICATION_LOG_REPOSITORY;
import static com.fji.notification.utils.TestConstants.MOCK_USER_ID;
import static com.fji.notification.utils.TestConstants.MOCK_USER_ID_UUID;
import static com.fji.notification.utils.TestConstants.TEST_NOTIFICATION_MESSAGE_ID;
import static com.fji.notification.utils.TestConstants.TEST_UUID_2_STRING;
import static com.fji.notification.utils.TestConstants.TEST_UUID_STRING;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NotificationLogRepositoryH2ImplTest {

    @Autowired
    @Qualifier(H2_NOTIFICATION_LOG_REPOSITORY)
    private NotificationLogRepositoryH2Impl notificationLogRepositoryH2;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        int rowsDeleted = jdbcTemplate.update("DELETE FROM notification_log");
        log.info("Prepare test: {} deleted item from posted_messages table", rowsDeleted);
    }

    @Test
//    @Sql(statements = {
//            "INSERT INTO notification_log (id, message_id, subscriber_id, channel_type, published, status, send_date) VALUES ('" + TEST_UUID_STRING     + "', '" + TEST_NOTIFICATION_MESSAGE_ID + "', '" + MOCK_USER_ID + "', '" + ChannelEnum.EMAIL.getChannel() + "', TRUE  , '" + NotificationStatus.SENT.getValue()    + "', NOW()",
//            "INSERT INTO notification_log (id, message_id, subscriber_id, channel_type, published, status, send_date) VALUES ('" + TEST_UUID_2_STRING   + "', '" + TEST_NOTIFICATION_MESSAGE_ID + "', '" + MOCK_USER_ID + "', '" + ChannelEnum.PUSH.getChannel()  + "', FALSE , '" + NotificationStatus.PENDING.getValue() + "', NOW()"
//    })
    void getAllNotificationLogsTest() {
        jdbcTemplate.update("INSERT INTO notification_log (id, message_id, subscriber_id, channel_type, published, status, send_date) VALUES ('" + TEST_UUID_STRING + "', '" + TEST_NOTIFICATION_MESSAGE_ID + "', '" + MOCK_USER_ID + "', '" + ChannelEnum.EMAIL.getChannel() + "', TRUE  , '" + NotificationStatus.SENT.getValue() + "', NOW())");
        jdbcTemplate.update("INSERT INTO notification_log (id, message_id, subscriber_id, channel_type, published, status, send_date) VALUES ('" + TEST_UUID_2_STRING + "', '" + TEST_NOTIFICATION_MESSAGE_ID + "', '" + MOCK_USER_ID + "', '" + ChannelEnum.PUSH.getChannel() + "', FALSE , '" + NotificationStatus.PENDING.getValue() + "', NULL)");
        List<NotificationLog> notificationLogList = notificationLogRepositoryH2.getAllNotificationLogs();
        assertNotNull(notificationLogList);
        assertEquals(2, notificationLogList.size());
        assertTrue(notificationLogList.stream().map(NotificationLog::getPostedMessageId).allMatch(TEST_NOTIFICATION_MESSAGE_ID::equals));
        assertTrue(notificationLogList.stream().map(NotificationLog::getSubscriberId).map(UUID::toString).allMatch(MOCK_USER_ID::equals));
        notificationLogList.forEach(notificationLog -> {
                    assertNotNull(notificationLog.getId());
                    if (ChannelEnum.EMAIL.equals(notificationLog.getChannelType())) {
                        assertTrue(notificationLog.isPublished());
                        assertEquals(NotificationStatus.SENT, notificationLog.getStatus());
                        assertNotNull(notificationLog.getSentTimeStamp());
                    } else {
                        assertFalse(notificationLog.isPublished());
                        assertEquals(NotificationStatus.PENDING, notificationLog.getStatus());
                    }
                }
        );
    }

    @Test
    void insertNotificationLogTest() {
        final NotificationLog expectedNotificationLog = getNotificationLogTestObj();

        assertNull(expectedNotificationLog.getId());

        int rowsUpdated = notificationLogRepositoryH2.insertNotificationLog(expectedNotificationLog);

        assertEquals(1, rowsUpdated);
        List<NotificationLog> notificationLogs = notificationLogRepositoryH2.getAllNotificationLogs();
        assertEquals(1, notificationLogs.size());
        NotificationLog storedNotificationLog = notificationLogs.get(0);
        assertNotNull(storedNotificationLog.getId());
        verifyNotificationLogs(expectedNotificationLog, storedNotificationLog);
    }

    @Test
    void insertBatchOfNotificationLogTest() {
        List<NotificationLog> expectedNotificationLogs = getTestListOfNotificationLogs();
        assertTrue(expectedNotificationLogs.stream().map(NotificationLog::getId).allMatch(Objects::isNull));

        int rowsUpdated = notificationLogRepositoryH2.insertBatchOfNotificationLog(expectedNotificationLogs);

        assertEquals(expectedNotificationLogs.size(), rowsUpdated);
        List<NotificationLog> notificationLogs = notificationLogRepositoryH2.getAllNotificationLogs();
        assertEquals(expectedNotificationLogs.size(), notificationLogs.size());
        for(int index = 0; index < notificationLogs.size(); index++ ){
            verifyNotificationLogs(expectedNotificationLogs.get(index), notificationLogs.get(index));
        }
    }

    private static void verifyNotificationLogs(NotificationLog expectedNotificationLog,
                                               NotificationLog storedNotificationLog) {
        assertNotNull(storedNotificationLog.getId());
        assertEquals(expectedNotificationLog.getPostedMessageId()   , storedNotificationLog.getPostedMessageId());
        assertEquals(expectedNotificationLog.getSubscriberId()      , storedNotificationLog.getSubscriberId());
        assertEquals(expectedNotificationLog.getChannelType()       , storedNotificationLog.getChannelType());
        assertEquals(expectedNotificationLog.isPublished()          , storedNotificationLog.isPublished());
        assertEquals(expectedNotificationLog.getStatus()            , storedNotificationLog.getStatus());
        assertEquals(expectedNotificationLog.getSentTimeStamp()     , storedNotificationLog.getSentTimeStamp());
    }
    private static List<NotificationLog> getTestListOfNotificationLogs() {
        int items = new Random().nextInt(9) + 1;
        return IntStream.range(0, items).mapToObj(i -> getNotificationLogTestObj()).collect(Collectors.toList());
    }

    private static NotificationLog getNotificationLogTestObj() {
        return NotificationLog.builder()
                .postedMessageId(TEST_NOTIFICATION_MESSAGE_ID)
                .subscriberId(MOCK_USER_ID_UUID)
                .channelType(ChannelEnum.EMAIL)
                .published(true)
                .status(NotificationStatus.SENT)
                .sentTimeStamp(LocalDateTime.now())
                .build();
    }
}