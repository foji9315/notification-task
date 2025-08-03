package com.fji.notification.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private Set<CategoryEnum> subscribed;
    private Set<ChannelEnum> channels;
}
