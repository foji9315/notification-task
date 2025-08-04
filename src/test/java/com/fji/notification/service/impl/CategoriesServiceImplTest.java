package com.fji.notification.service.impl;

import com.fji.notification.model.CategoryEnum;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class CategoriesServiceImplTest {

    private CategoriesServiceImpl categoriesService;

    @BeforeEach
    void setUp(){
        categoriesService = new CategoriesServiceImpl();
    }

    @Test
    void getAllCategoryNamesTest() {
        List<String> categories = categoriesService.getAllCategoryNames();
        assertEquals(CategoryEnum.values().length - 1, categories.size());
        Set<String> EXPECTED_CATEGORIES = Arrays.stream(CategoryEnum.values()).map(CategoryEnum::getName).filter(StringUtils::isNotBlank).collect(Collectors.toSet());
        categories.forEach(category -> {
            assertTrue(EXPECTED_CATEGORIES.contains(category), "Category does not valid");
        });
    }
}