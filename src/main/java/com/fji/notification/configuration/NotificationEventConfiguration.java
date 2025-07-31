package com.fji.notification.configuration;

import com.fji.notification.listeners.ChannelListener;
import com.fji.notification.listeners.impl.EmailNotificationChannel;
import com.fji.notification.listeners.impl.PushNotificationChannel;
import com.fji.notification.listeners.impl.SMSNotificationChannel;
import com.fji.notification.model.Category;
import com.fji.notification.model.User;
import com.fji.notification.publisher.NotificationEventManager;
import com.fji.notification.repository.impl.CategoriesRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Configuration
public class NotificationEventConfiguration {

    public static final String SPORT_EVENT_MANAGER = "SportEventManager";
    public static final String FINANCE_EVENT_MANAGER = "FinanceEventManager";
    public static final String MOVIE_EVENT_MANAGER = "MovieEventManager";

    public static final List<User> mockUsers = getRandomUsers();

    private static List<User> getRandomUsers() {

        Category category = Category.builder()
                .id(CategoriesRepositoryImpl.CategoryDAO.SPORT.getId())
                .name(CategoriesRepositoryImpl.CategoryDAO.SPORT.getName())
                .build();

        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .name("Rocky")
                .email("user@mock.com")
                .phoneNumber("12345")
                .subscribed(List.of(category))
                .channels(List.of("SMS", "email", "push"))
                .build();
        return Collections.singletonList(user);
    }

    @Bean(SPORT_EVENT_MANAGER)
    public NotificationEventManager getSportEventManager() {

        NotificationEventManager notificationEventManager = new NotificationEventManager();
        ChannelListener emailUserListener = new EmailNotificationChannel(mockUsers.get(0));
        ChannelListener smsUserListener = new SMSNotificationChannel(mockUsers.get(0));
        ChannelListener pushUserListener = new PushNotificationChannel(mockUsers.get(0));
        List<ChannelListener> channelListeners = List.of(emailUserListener, smsUserListener, pushUserListener);
        channelListeners.forEach(notificationEventManager::subscribe);
        return notificationEventManager;
    }

    @Bean(FINANCE_EVENT_MANAGER)
    public NotificationEventManager getFinanceEventManager() {

        NotificationEventManager notificationEventManager = new NotificationEventManager();
        return notificationEventManager;
    }

    @Bean(MOVIE_EVENT_MANAGER)
    public NotificationEventManager getMoviesEventManager() {

        NotificationEventManager notificationEventManager = new NotificationEventManager();
        return notificationEventManager;
    }
}
