package com.fji.notification.repository;


import com.fji.notification.model.Category;

import java.util.List;

public interface CategoriesRepository {

    List<Category> getAllCategories();
}
