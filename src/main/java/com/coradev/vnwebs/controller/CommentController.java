package com.coradev.vnwebs.controller;

import com.coradev.vnwebs.model.Comment;
import com.coradev.vnwebs.model.User;
import com.coradev.vnwebs.service.CommentService;
import com.coradev.vnwebs.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;

    @Autowired
    public CommentController(CommentService commentService, PostService postService) {
        this.commentService = commentService;
        this.postService = postService;
    }


    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{postId}")
    public String comments(@PathVariable Long postId, Model model){
        model.addAttribute("comments", commentService.listCommentByPostId(postId));
        return "post :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session){
        Long postId = comment.getPost().getId();
        comment.setPost(postService.getPost(postId));
        User user = (User) session.getAttribute("user");
        if (user != null){
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
            System.out.println(comment.isAdminComment());
        } else{
            comment.setAvatar(avatar);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + postId;
    }
}
