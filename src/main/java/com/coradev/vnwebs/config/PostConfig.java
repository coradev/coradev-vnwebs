package com.coradev.vnwebs.config;

import com.coradev.vnwebs.model.Category;
import com.coradev.vnwebs.model.Post;
import com.coradev.vnwebs.model.Tag;
import com.coradev.vnwebs.model.User;
import com.coradev.vnwebs.repository.PostRepository;
import com.coradev.vnwebs.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Configuration
public class PostConfig {
    @Bean
    CommandLineRunner commandLineRunner(PostRepository postRepository, UserRepository userRepository) {
        User _user = User.builder().fullName("Quang Truong").username("qt1st").password("1234").avatar("images/avatar/admin.png").biography("Handsome").build();
        Post _post = new Post("The Title Of Post",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                new Date(),
                "images/posts/post1.jpg",
                true,
                _user
        );
        _user.setPosts(Collections.singleton(_post));
        return args -> {
            userRepository.saveAndFlush(_user);
        };
    }
}
