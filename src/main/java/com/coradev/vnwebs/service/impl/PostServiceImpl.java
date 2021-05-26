package com.coradev.vnwebs.service.impl;

import com.coradev.vnwebs.NotFoundException;
import com.coradev.vnwebs.model.Category;
import com.coradev.vnwebs.model.Post;
import com.coradev.vnwebs.repository.PostRepository;
import com.coradev.vnwebs.service.PostService;
import com.coradev.vnwebs.util.MarkdownUtils;
import com.coradev.vnwebs.vo.PostQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post getPost(Long id) {
        return postRepository.findById(id).get();
    }

    @Transactional
    @Override
    public Post getAndConvert(Long id) {
        Post post = postRepository.findById(id).get();
        if(post == null){
            throw new NotFoundException("This blog doesn't exist!");
        }
        Post p = new Post();
        BeanUtils.copyProperties(post, p);
        String content = p.getContent();
        p.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        postRepository.updateViews(id);
        return p;
    }

    @Override
    public Page<Post> listPost(Pageable pageable, PostQuery post) {
        return postRepository.findAll(new Specification<Post>() {
            @Override
            public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(post.getTitle() != null && !"".equals(post.getTitle())){
                    predicates.add(criteriaBuilder.like(root.get("title"), "%" + post.getTitle() + "%"));
                }
                if(post.getCategoryId() != null){
                    predicates.add(criteriaBuilder.equal(root.<Category>get("category").get("id"), post.getCategoryId()));
                }
                if(post.isRecommend()){
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"), post.isRecommend()));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Override
    public Page<Post> listPost(Pageable pageable) {
        return null;
    }

    @Override
    public Page<Post> listPost(Long tagId, Pageable pageable) {
        return null;
    }

    @Override
    public Page<Post> listPost(String query, Pageable pageable) {
        return null;
    }

    @Override
    public List<Post> listRecommendPostsTop(Integer size) {
        return null;
    }

    @Override
    public Map<String, List<Post>> archivePost() {
        return null;
    }

    @Override
    public Long countPost() {
        return null;
    }

    @Override
    public Post savePost(Post post) {
        return null;
    }

    @Override
    public Post updateBlog(Long id, Post post) {
        return null;
    }

    @Override
    public void deletePost(Long id) {

    }
}
