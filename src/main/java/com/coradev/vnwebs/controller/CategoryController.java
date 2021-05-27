package com.coradev.vnwebs.controller;

import com.coradev.vnwebs.model.Category;
import com.coradev.vnwebs.service.CategoryService;
import com.coradev.vnwebs.service.PostService;
import com.coradev.vnwebs.vo.PostQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

//@Controller
public class CategoryController {

    private final CategoryService categoryService;
    private final PostService postService;

    @Autowired
    public CategoryController(CategoryService categoryService, PostService postService) {
        this.categoryService = categoryService;
        this.postService = postService;
    }

    @GetMapping("/category/{id}")
    public String categories(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Long id, Model model){
        List<Category> categories = categoryService.listCategoryTop(10000);
        if (id == -1){
            id = categories.get(0).getId();
        }
        PostQuery postQuery = new PostQuery();
        postQuery.setCategoryId(id);
        model.addAttribute("categories",categories);
        model.addAttribute("page", postService.listPost(pageable, postQuery));
        model.addAttribute("activeCategoryId", id);
        return "categories";
    }
}
