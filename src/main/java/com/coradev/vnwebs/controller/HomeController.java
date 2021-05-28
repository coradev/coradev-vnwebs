package com.coradev.vnwebs.controller;

import com.coradev.vnwebs.service.CategoryService;
import com.coradev.vnwebs.service.PostService;
import com.coradev.vnwebs.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final PostService postService;

    private final CategoryService categoryService;

    private final TagService tagService;

    @Autowired
    public HomeController(PostService postService, CategoryService categoryService, TagService tagService) {
        this.postService = postService;
        this.categoryService = categoryService;
        this.tagService = tagService;
    }

    @GetMapping("/")
    public String index(@PageableDefault(size = 4, sort = "updateTime", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        model.addAttribute("page", postService.listPost(pageable));
        model.addAttribute("categories", categoryService.listCategoryTop(5));
        model.addAttribute("tags", tagService.listTagTop(10));
        model.addAttribute("recommendPosts", postService.listRecommendPostsTop(8));
        return "index";
    }

    @GetMapping("/search")
    public String search(@PageableDefault(size = 6, sort = "updateTime",
                            direction = Sort.Direction.DESC) Pageable pageable,
                            @RequestParam String query, Model model) {
        model.addAttribute("page", postService.listPost("%" + query + "%", pageable));
        model.addAttribute("query", query);
        return "search";
    }

    @GetMapping("/post/{id}")
    public String post(@PathVariable Long id, Model model) {
        model.addAttribute("post", postService.getAndConvert(id));
        return "post";
    }

    @GetMapping("/footer/newpost")
    public String newPosts(Model model) {
        model.addAttribute("newposts", postService.listRecommendPostsTop(4));
        return "_fragments :: newPostList";
    }


}
