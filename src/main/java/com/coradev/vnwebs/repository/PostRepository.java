package com.coradev.vnwebs.repository;

import com.coradev.vnwebs.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByPublished(boolean published);

    List<Post> findByTitleContaining(String title);
}
