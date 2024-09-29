package com.kaniya.spring.service.category;

import com.kaniya.spring.model.Category;

import java.util.List;

public interface ICategoryService {
    Category getCategoryById(long id);
    List<Category> getAllCategories();
    Category getCategoryByName(String name);
    Category addCategory(Category category);
    Category updateCategory(Category category , long id);
    void deleteCategory(long id);

}
