package com.fji.notification.configuration;

import com.fji.notification.facrory.ChannelListenerFactory;
import com.fji.notification.facrory.impl.EmailNotificationChannelFactory;
import com.fji.notification.facrory.impl.PushNotificationChannelFactory;
import com.fji.notification.facrory.impl.SMSNotificationChannelFactory;
import com.fji.notification.model.CategoryEnum;
import com.fji.notification.model.ChannelEnum;
import com.fji.notification.model.User;
import com.fji.notification.publisher.NotificationEventManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static java.util.Arrays.asList;

@Configuration
public class NotificationEventConfiguration {

    public static final String SPORT_EVENT_MANAGER = "SportEventManager";
    public static final String FINANCE_EVENT_MANAGER = "FinanceEventManager";
    public static final String MOVIE_EVENT_MANAGER = "MovieEventManager";

    public static final List<User> mockUsers = getRandomUsers();
    static Map<ChannelEnum, ChannelListenerFactory> sportFactories;
    static Map<ChannelEnum, ChannelListenerFactory> financeFactories;
    static Map<ChannelEnum, ChannelListenerFactory> movieFactories;

    private static List<User> getRandomUsers() {

        User rocky = User.builder()
                .id(UUID.randomUUID().toString())
                .name("Rocky")
                .email("rocky@mock.com")
                .phoneNumber("55-55-55-55-55")
                .subscribed(Set.of(CategoryEnum.SPORT, CategoryEnum.MOVIES))
                .channels(Set.of(ChannelEnum.EMAIL, ChannelEnum.SMS))
                .build();

        User checo = User.builder()
                .id(UUID.randomUUID().toString())
                .name("Checo")
                .email("checo@mock.com")
                .phoneNumber("11-11-11-11-11")
                .subscribed(Set.of(CategoryEnum.SPORT, CategoryEnum.FINANCE))
                .channels(Set.of(ChannelEnum.EMAIL, ChannelEnum.PUSH))
                .build();

        User mali = User.builder()
                .id(UUID.randomUUID().toString())
                .name("Mali")
                .email("mali@mock.com")
                .phoneNumber("99-99-99-99-99")
                .subscribed(Set.of(CategoryEnum.SPORT, CategoryEnum.MOVIES, CategoryEnum.FINANCE))
                .channels(Set.of(ChannelEnum.EMAIL, ChannelEnum.SMS, ChannelEnum.PUSH))
                .build();

        return asList(rocky, checo, mali);
    }

    @Bean(SPORT_EVENT_MANAGER)
    public NotificationEventManager getSportEventManager() {
        NotificationEventManager notificationEventManager = new NotificationEventManager();
        sportFactories = Map.of(
                ChannelEnum.EMAIL, new EmailNotificationChannelFactory(notificationEventManager),
                ChannelEnum.PUSH, new PushNotificationChannelFactory(notificationEventManager),
                ChannelEnum.SMS, new SMSNotificationChannelFactory(notificationEventManager)
        );
        mockUsers.stream()
                .filter(user -> user.getSubscribed().contains(CategoryEnum.SPORT))
                .forEach(user -> subscribeToEachChannel(user, sportFactories));
        return notificationEventManager;
    }

    @Bean(FINANCE_EVENT_MANAGER)
    public NotificationEventManager getFinanceEventManager() {
        NotificationEventManager notificationEventManager = new NotificationEventManager();
        financeFactories = Map.of(
                ChannelEnum.EMAIL, new EmailNotificationChannelFactory(notificationEventManager),
                ChannelEnum.PUSH, new PushNotificationChannelFactory(notificationEventManager),
                ChannelEnum.SMS, new SMSNotificationChannelFactory(notificationEventManager)
        );
        mockUsers.stream()
                .filter(user -> user.getSubscribed().contains(CategoryEnum.FINANCE))
                .forEach(user -> subscribeToEachChannel(user, financeFactories));
        return notificationEventManager;
    }

    @Bean(MOVIE_EVENT_MANAGER)
    public NotificationEventManager getMoviesEventManager() {
        NotificationEventManager notificationEventManager = new NotificationEventManager();
        movieFactories = Map.of(
                ChannelEnum.EMAIL, new EmailNotificationChannelFactory(notificationEventManager),
                ChannelEnum.PUSH, new PushNotificationChannelFactory(notificationEventManager),
                ChannelEnum.SMS, new SMSNotificationChannelFactory(notificationEventManager)
        );
        mockUsers.stream()
                .filter(user -> user.getSubscribed().contains(CategoryEnum.MOVIES))
                .forEach(user -> subscribeToEachChannel(user, movieFactories));
        return notificationEventManager;
    }

    private static void subscribeToEachChannel(User user, Map<ChannelEnum, ChannelListenerFactory> channelFactoryMap) {
        user.getChannels().forEach(
                channelEnum -> channelFactoryMap.get(channelEnum).subscribe(user)
        );
    }
}
