package com.fji.notification.repository.impl;

import com.fji.notification.model.CategoryEnum;
import com.fji.notification.model.NotificationMessage;
import com.fji.notification.repository.MessageLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.fji.notification.model.CategoryEnum.FINANCE;
import static com.fji.notification.model.CategoryEnum.SPORT;
import static com.fji.notification.repository.impl.PublishedMessageRepositoryH2Impl.H2_MSG_LOG_REPOSITORY;
import static com.fji.notification.utils.TestConstants.TEST_UUID;
import static com.fji.notification.utils.TestConstants.TEST_UUID_2_STRING;
import static com.fji.notification.utils.TestConstants.TEST_UUID_STRING;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PublishedMessageRepositoryH2ImplTest {

    private static final String TEST_MESSAGE = "This is a test message";

    @Autowired
    @Qualifier(H2_MSG_LOG_REPOSITORY)
    private MessageLogRepository messageLogRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        int rowDeleted = jdbcTemplate.update("DELETE FROM posted_messages");
        log.info("Prepare test: {} deleted item from posted_messages table", rowDeleted);
    }

    @Test
//    Seems like @Sql not working properly for h2 db test
//    @Sql(statements = {
//            "INSERT INTO posted_messages(id, message, category, created_at) VALUES ('" + TEST_UUID_STRING + "', '" + TEST_MESSAGE + "', 'SPORT'  , NOW())",
//            "INSERT INTO posted_messages(id, message, category, created_at) VALUES ('" + TEST_UUID_2_STRING + "', '" + TEST_MESSAGE + "', 'FINANCE', NOW())"
//    })
    void getAllNotificationMessagesTest() {
        jdbcTemplate.update("INSERT INTO posted_messages(id, message, category, created_at) VALUES ('" + TEST_UUID_STRING + "', '" + TEST_MESSAGE + "', 'SPORT'  , NOW())");
        jdbcTemplate.update("INSERT INTO posted_messages(id, message, category, created_at) VALUES ('" + TEST_UUID_2_STRING + "', '" + TEST_MESSAGE + "', 'FINANCE', NOW())");
        List<NotificationMessage> notificationMessageList = messageLogRepository.getAllNotificationMessages();
        assertNotNull(notificationMessageList);
        assertEquals(2, notificationMessageList.size());
        assertTrue(notificationMessageList.stream().map(NotificationMessage::getMessage).allMatch(TEST_MESSAGE::equals));
        Set<CategoryEnum> categoriesFromMessages = notificationMessageList.stream().map(NotificationMessage::getCategory).collect(Collectors.toSet());
        assertTrue(categoriesFromMessages.contains(SPORT));
        assertTrue(categoriesFromMessages.contains(FINANCE));
    }

    @Test
    void insertNewMessageTest() {
        NotificationMessage notificationMessage =
                NotificationMessage.builder()
                        .id(TEST_UUID)
                        .category(SPORT)
                        .message(TEST_MESSAGE)
                        .build();
        Optional<NotificationMessage> maybeNotificationMessageStored = messageLogRepository.insertNewMessage(notificationMessage);

        assertTrue(maybeNotificationMessageStored.isPresent());
        assertEquals(notificationMessage, maybeNotificationMessageStored.get());
     }
}