package com.coradev.vnwebs.controller.admin;

import com.coradev.vnwebs.model.Post;
import com.coradev.vnwebs.model.User;
import com.coradev.vnwebs.service.CategoryService;
import com.coradev.vnwebs.service.PostService;
import com.coradev.vnwebs.service.TagService;
import com.coradev.vnwebs.vo.PostQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class PostController {

    private static final String INPUT = "admin/post-input";
    private static final String LIST = "admin/posts";
    private static final String REDIRECT_LIST = "redirect:/admin/posts";


    private final PostService postService;
    private final CategoryService categoryService;
    private final TagService tagService;

    @Autowired
    public PostController(PostService postService, CategoryService categoryService, TagService tagService) {
        this.postService = postService;
        this.categoryService = categoryService;
        this.tagService = tagService;
    }

    @GetMapping("/posts")
    public String list(@PageableDefault(size = 2, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                       PostQuery post, Model model) {
        model.addAttribute("types", categoryService.listCategory());
        model.addAttribute("page", postService.listPost(pageable, post));
        return LIST;
    }

    @PostMapping("/posts/search")
    public String search(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         PostQuery post, Model model) {
        model.addAttribute("page", postService.listPost(pageable, post));
        return "admin/posts :: postList";
    }

    @GetMapping("/posts/input")
    public String input(Model model) {
        setCategoryAndTag(model);
        model.addAttribute("post", new Post());
        return INPUT;
    }

    public void setCategoryAndTag(Model model) {
        model.addAttribute("categories", categoryService.listCategory());
        model.addAttribute("tags", tagService.listTag());
    }

    @GetMapping("/posts/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        setCategoryAndTag(model);
        Post post = postService.getPost(id);
        post.init();
        model.addAttribute("post", post);
        return INPUT;
    }

    @PostMapping("/posts")
    public String post(Post post, RedirectAttributes attributes, HttpSession session) {
        post.setUser((User) session.getAttribute("user"));
        post.setCategory(categoryService.getCategory(post.getCategory().getId()));
        post.setTags(tagService.listTag(post.getTagIds()));

        Post _post;
        if (post.getId() == null) {
            _post = postService.savePost(post);
        } else {
            _post = postService.updatePost(post.getId(), post);
        }

        if (_post == null) {
            attributes.addFlashAttribute("message", "add failed!");
        } else {
            attributes.addFlashAttribute("message", "add successful!");
        }
        return REDIRECT_LIST;
    }

    @GetMapping("/posts/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        postService.deletePost(id);
        attributes.addFlashAttribute("message", "delete success!");
        return REDIRECT_LIST;
    }

}
