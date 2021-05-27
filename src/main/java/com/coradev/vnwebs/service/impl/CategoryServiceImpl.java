package com.coradev.vnwebs.service.impl;

import com.coradev.vnwebs.NotFoundException;
import com.coradev.vnwebs.model.Category;
import com.coradev.vnwebs.repository.CategoryRepository;
import com.coradev.vnwebs.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    @Override
    public Category getCategory(Long id) {
        return categoryRepository.findById(id).get();
    }

    @Transactional
    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Transactional
    @Override
    public Page<Category> listCategory(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public List<Category> listCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> listCategoryTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "posts.size");
        Pageable pageable = PageRequest.of(0, size, sort);
        return categoryRepository.findTop(pageable);
    }

    @Transactional
    @Override
    public Category updateCategory(Long id, Category category) {
        Category _category = categoryRepository.findById(id).get();
        if (_category == null) {
            throw new NotFoundException("This category doesn't exist!");
        }
        BeanUtils.copyProperties(category, _category);
        return categoryRepository.save(_category);
    }

    @Transactional
    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
