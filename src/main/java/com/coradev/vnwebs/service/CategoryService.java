package com.coradev.vnwebs.service;

import com.coradev.vnwebs.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {

    Category saveCategory(Category category);

    Category getCategory(Long id);

    Category getCategoryByName(String name);

    Page<Category> listCategory(Pageable pageable);

    List<Category> listCategory();

    List<Category> listCategoryTop(Integer size);

    Category updateCategory(Long id, Category category);

    void deleteCategory(Long id);
}
