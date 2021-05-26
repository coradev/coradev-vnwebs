package com.coradev.vnwebs.repository;

import com.coradev.vnwebs.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
