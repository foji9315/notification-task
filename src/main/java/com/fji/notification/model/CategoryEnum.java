package com.fji.notification.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public enum CategoryEnum {
    SPORT("SPORT"),
    FINANCE("FINANCE"),
    MOVIES("MOVIES");

    private final String name;
}
