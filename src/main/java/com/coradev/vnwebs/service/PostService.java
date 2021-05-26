package com.coradev.vnwebs.service;

import com.coradev.vnwebs.model.Post;
import com.coradev.vnwebs.vo.PostQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface PostService {

    Post getPost(Long id);
    Post getAndConvert(Long id);
    Page<Post> listPost(Pageable pageable, PostQuery post);
    Page<Post> listPost(Pageable pageable);
    Page<Post> listPost(Long tagId, Pageable pageable);
    Page<Post> listPost(String query, Pageable pageable);
    List<Post> listRecommendPostsTop(Integer size);
    Map<String, List<Post>> archivePost();
    Long countPost();
    Post savePost(Post post);
    Post updateBlog(Long id, Post post);
    void deletePost(Long id);

}
