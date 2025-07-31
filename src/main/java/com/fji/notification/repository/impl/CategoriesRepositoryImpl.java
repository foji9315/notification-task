package com.fji.notification.repository.impl;

import com.fji.notification.model.Category;
import com.fji.notification.repository.CategoriesRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class CategoriesRepositoryImpl implements CategoriesRepository {

    @AllArgsConstructor
    @Getter
    public enum CategoryDAO {
        SPORT(UUID.randomUUID(),"Sport"),
        FINANCE(UUID.randomUUID(),"Finance"),
        MOVIES(UUID.randomUUID(),"Movies");

        private final UUID id;
        private final String name;
    }

    @Override
    public List<Category> getAllCategories() {
        return Arrays.stream(CategoryDAO.values())
                .map(categoryDAO -> Category.builder()
                        .id(categoryDAO.getId())
                        .name(categoryDAO.getName())
                        .build()
                )
                .collect(Collectors.toList());
    }
}
