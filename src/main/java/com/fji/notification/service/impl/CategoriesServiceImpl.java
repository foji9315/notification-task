package com.fji.notification.service.impl;

import com.fji.notification.model.Category;
import com.fji.notification.repository.CategoriesRepository;
import com.fji.notification.service.CategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriesServiceImpl implements CategoriesService {

    private final CategoriesRepository categoriesRepository;


    @Override
    public List<String> getAllCategoryNames() {
        return categoriesRepository.getAllCategories().stream().map(Category::getName).sorted().collect(Collectors.toList());
    }
}
