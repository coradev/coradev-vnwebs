package com.coradev.vnwebs.controller;

import com.coradev.vnwebs.model.Tag;
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

import java.util.List;

//@Controller
public class TagController {
    private final PostService postService;
    private final TagService tagService;

    @Autowired
    public TagController(PostService postService, TagService tagService) {
        this.postService = postService;
        this.tagService = tagService;
    }

    @GetMapping("/tags/{id}")
    public String tags(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                       @PathVariable Long id, Model model){
        List<Tag> tags = tagService.listTagTop(10000);
        if (id == -1){
            id = tags.get(0).getId();
        }
        model.addAttribute("tags",tags);
        model.addAttribute("page", postService.listPost(id, pageable));
        model.addAttribute("activeTagId", id);
        return "tags";
    }
}
