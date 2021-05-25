package com.coradev.vnwebs.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.coradev.vnwebs.model.Post;
import com.coradev.vnwebs.model.User;
import com.coradev.vnwebs.repository.PostRepository;
import com.coradev.vnwebs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@CrossOrigin(origins = "http://localhost:8888")
@RestController
@RequestMapping("/api")
public class PostController {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostController(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }



    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPosts(@RequestParam(required = false) String title) {
        try {
            List<Post> posts = new ArrayList<>();
            if (title == null) {
                postRepository.findAll().forEach(posts::add);
            } else {
                postRepository.findByTitleContaining(title).forEach(posts::add);
            }
            if (posts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable("id") long id) {
        Optional<Post> postData = postRepository.findById(id);

        if (postData.isPresent()) {
            return new ResponseEntity<>(postData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/posts")
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        try {
            Post _post = postRepository.save(
                    new Post(post.getTitle(),
                            post.getContent(),
                            post.getCreatedDate(),
                            post.getCoverImage(),
                            post.isPublished(),
                            post.getUser(),
                            post.getCategories(),
                            post.getTags()
                    )
            );
            return new ResponseEntity<>(_post, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable("id") long id, @RequestBody Post post){
        Optional<Post> postData = postRepository.findById(id);
        if (postData.isPresent()){
            Post _post = postData.get();
            _post.setTitle(post.getTitle());
            _post.setContent(post.getContent());
            _post.setCreatedDate(post.getCreatedDate());
            _post.setCoverImage(post.getCoverImage());
            _post.setPublished(post.isPublished());
            _post.setCategories(post.getCategories());
            _post.setTags(post.getTags());
            return new ResponseEntity<>(postRepository.save(_post), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Post> deletePost(@PathVariable("id") long id){
        try {
            postRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/posts/published")
    public ResponseEntity<List<Post>> findByPublished(){
        try {
            List<Post> posts = postRepository.findByPublished(true);

            if (posts.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(posts, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
