package com.fji.notification.service.impl;

import com.fji.notification.model.CategoryEnum;
import com.fji.notification.service.CategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriesServiceImpl implements CategoriesService {

    @Override
    public List<String> getAllCategoryNames() {
        return Arrays.stream(CategoryEnum.values()).map(CategoryEnum::getName).toList();
    }
}
