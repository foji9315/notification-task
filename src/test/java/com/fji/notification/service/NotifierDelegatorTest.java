package com.fji.notification.service;

import com.fji.notification.model.CategoryEnum;
import com.fji.notification.model.NotificationMessage;
import com.fji.notification.service.notifiers.impl.FinanceNotifier;
import com.fji.notification.service.notifiers.impl.MovieNotifier;
import com.fji.notification.service.notifiers.impl.SportNotifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static com.fji.notification.utils.TestConstants.TEST_LOCAL_DATE_TIME;
import static com.fji.notification.utils.TestConstants.TEST_UUID;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

class NotifierDelegatorTest {

    private NotifierDelegator notifierDelegator;
    private final SportNotifier mockSportNotifier = mock(SportNotifier.class);
    private final FinanceNotifier mockFinanceNotifier = mock(FinanceNotifier.class);
    private final MovieNotifier mockMovieNotifier = mock(MovieNotifier.class);

    @BeforeEach
    void setUp() {
        notifierDelegator = new NotifierDelegator(
                mockSportNotifier,
                mockFinanceNotifier,
                mockMovieNotifier
        );
    }

    @ParameterizedTest
    @EnumSource(CategoryEnum.class)
    void notifyIncomingMessageTest(CategoryEnum category) {
        NotificationMessage notificationMessage =
                NotificationMessage.builder()
                        .id(TEST_UUID)
                        .category(category)
                        .createdAt(TEST_LOCAL_DATE_TIME)
                        .build();

        notifierDelegator.notifyIncomingMessage(notificationMessage);

        if(CategoryEnum.SPORT.equals(category)) {
            verify(mockSportNotifier).notifyIncomingMessage(notificationMessage);
        } else if (CategoryEnum.FINANCE.equals(category)) {
            verify(mockFinanceNotifier).notifyIncomingMessage(notificationMessage);
        } else if(CategoryEnum.MOVIES.equals(category)) {
            verify(mockMovieNotifier).notifyIncomingMessage(notificationMessage);
        } else {
            verifyNoInteractions(mockFinanceNotifier, mockMovieNotifier, mockFinanceNotifier);
        }
    }
}