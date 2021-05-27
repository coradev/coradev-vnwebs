package com.coradev.vnwebs.service;

import com.coradev.vnwebs.model.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> listCommentByPostId(Long postId);

    Comment saveComment(Comment comment);
}
