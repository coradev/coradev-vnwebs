package com.coradev.vnwebs.service.impl;

import com.coradev.vnwebs.NotFoundException;
import com.coradev.vnwebs.model.Category;
import com.coradev.vnwebs.model.Post;
import com.coradev.vnwebs.repository.PostRepository;
import com.coradev.vnwebs.service.PostService;
import com.coradev.vnwebs.util.MarkdownUtils;
import com.coradev.vnwebs.util.MyBeanUtils;
import com.coradev.vnwebs.vo.PostQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

@Service
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
        if (post == null) {
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
                if (post.getTitle() != null && !"".equals(post.getTitle())) {
                    predicates.add(criteriaBuilder.like(root.get("title"), "%" + post.getTitle() + "%"));
                }
                if (post.getCategoryId() != null) {
                    predicates.add(criteriaBuilder.equal(root.<Category>get("category").get("id"), post.getCategoryId()));
                }
                if (post.isRecommend()) {
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"), post.isRecommend()));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Override
    public Page<Post> listPost(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    public Page<Post> listPost(Long tagId, Pageable pageable) {
        return postRepository.findAll(new Specification<Post>() {
            @Override
            public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join join = root.join("tags");

                return criteriaBuilder.equal(join.get("id"), tagId);
            }
        }, pageable);
    }

    @Override
    public Page<Post> listPost(String query, Pageable pageable) {
        return postRepository.findByQuery(query, pageable);
    }

    @Override
    public List<Post> listRecommendPostsTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(0, size, sort);
        return postRepository.findTop(pageable);
    }

    @Override
    public Map<String, List<Post>> archivePost() {
        List<String> years = postRepository.findGroupYear();
        Map<String, List<Post>> map = new HashMap<>();
        for (String year : years) {
            map.put(year, postRepository.findByYear(year));
        }
        return map;
    }

    @Override
    public Long countPost() {
        return postRepository.count();
    }

    @Transactional
    @Override
    public Post savePost(Post post) {
        if (post.getId() == null) {
            post.setCreateTime(new Date());
            post.setUpdateTime(new Date());
            post.setView(0);
        } else {
            post.setUpdateTime(new Date());
        }
        return postRepository.save(post);
    }

    @Transactional
    @Override
    public Post updatePost(Long id, Post post) {
        Post _post = postRepository.findById(id).get();
        if (_post == null) {
            throw new NotFoundException("This update post doesn't exist");
        }
        BeanUtils.copyProperties(post, _post, MyBeanUtils.getNullProperty(post));
        post.setUpdateTime(new Date());
        return postRepository.save(_post);
    }

    @Transactional
    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
