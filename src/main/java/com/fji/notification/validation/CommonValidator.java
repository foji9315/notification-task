package com.fji.notification.validation;

import com.fji.notification.model.CategoryEnum;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static org.apache.commons.lang3.StringUtils.isBlank;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonValidator {


    public static boolean isNotValidCategoryValue(String categoryValue) {
        if(isBlank(categoryValue))
            return true;
        try {
            CategoryEnum.valueOf(categoryValue);
        } catch (IllegalArgumentException e) {
            return true;
        }
        return false;
    }
}
