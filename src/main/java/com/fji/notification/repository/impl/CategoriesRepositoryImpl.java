package com.fji.notification.repository.impl;

import com.fji.notification.model.Category;
import com.fji.notification.model.CategoryEnum;
import com.fji.notification.repository.CategoriesRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CategoriesRepositoryImpl implements CategoriesRepository {

    @Override
    public List<Category> getAllCategories() {
        return Arrays.stream(CategoryEnum.values())
                .map(categoryDAO -> Category.builder()
                        .name(categoryDAO.getName())
                        .build())
                .collect(Collectors.toList());
    }
}
