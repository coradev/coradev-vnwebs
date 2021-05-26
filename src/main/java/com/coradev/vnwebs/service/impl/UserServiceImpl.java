package com.coradev.vnwebs.service.impl;

import com.coradev.vnwebs.model.User;
import com.coradev.vnwebs.repository.UserRepository;
import com.coradev.vnwebs.service.UserService;
import com.coradev.vnwebs.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User checkUser(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
    }
}
