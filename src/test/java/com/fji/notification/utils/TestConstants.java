package com.fji.notification.utils;

import com.fji.notification.model.CategoryEnum;
import com.fji.notification.model.ChannelEnum;
import com.fji.notification.model.NotificationMessage;
import com.fji.notification.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestConstants {

    public static final String MESSAGE_MORE_THAN_200_CHARACTERS = "Millions of motorists have been denied a path to claim compensation for hidden commissions paid on car loans following a Supreme Court ruling. The UKs highest court sided with finance companies in two out of three crucial test cases focusing on commission payments made by banks and other credit providers to car dealers";
    public static final String MESSAGE_WITH_200_CHARACTERS = "Millions of motorists have been denied a path to claim compensation for hidden commissions paid on car loans following a Supreme Court ruling. The UKs highest court sided with finance companies in tw.";
    // MOCK USER DATA
    public static final String MOCK_USER_ID = UUID.randomUUID().toString();
    public static final String MOCK_USER_NAME = "MOCK_USER";
    public static final String MOCK_EMAIL = "mock@email.com";
    public static final String MOCK_PHONE_NUMBER = "99-99-99-99-99";
    public static final Set<CategoryEnum> MOCK_USER_SUBSCRIBED = Set.of(CategoryEnum.FINANCE, CategoryEnum.MOVIES);
    public static final Set<ChannelEnum> MOCK_USER_CHANNELS = Set.of(ChannelEnum.EMAIL, ChannelEnum.PUSH);
    public static final User MOCK_USER = User.builder().name(MOCK_USER_NAME).id(MOCK_USER_ID).email(MOCK_EMAIL).phoneNumber(MOCK_PHONE_NUMBER).subscribed(MOCK_USER_SUBSCRIBED).channels(MOCK_USER_CHANNELS).build();

    // Mock Notification Message
    public static final UUID TEST_NOTIFICATION_MESSAGE_ID = UUID.randomUUID();
    public static final CategoryEnum TEST_NOTIFICATION_CATEGORY = CategoryEnum.MOVIES;
    public static final NotificationMessage TEST_NOTIFICATION_MESSAGE = NotificationMessage.builder().id(TEST_NOTIFICATION_MESSAGE_ID).category(TEST_NOTIFICATION_CATEGORY).build();
    public static final NotificationMessage TEST_EXCEPTION_NOTIFICATION_MESSAGE = NotificationMessage.builder().id(TEST_NOTIFICATION_MESSAGE_ID).message("EXCEPTION_TEST").category(TEST_NOTIFICATION_CATEGORY).build();

    // Generals
    public static final String TEST_UUID_STRING = "0544271b-b563-464f-9009-aacb0b03fafe";
    public static final UUID TEST_UUID = UUID.fromString("0544271b-b563-464f-9009-aacb0b03fafe");
    public static final String TEST_TIME_STAMP_STRING = "2025-08-03 04:39:58";
    public static final LocalDateTime TEST_LOCAL_DATE_TIME = LocalDateTime.of(2025, 8,3,4,39,58,0);


}
