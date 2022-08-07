package com.example.MYSTORE.PRODUCTS.Repository;

import com.example.MYSTORE.PRODUCTS.Model.Category;
import com.example.MYSTORE.PRODUCTS.Model.Tea;

public interface CustomCategoryRepository {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    void updateCategory(Category category);
    void updateCategoryAndTea(Category category, Tea tea);
    void saveNewCategory(Category category);
}
