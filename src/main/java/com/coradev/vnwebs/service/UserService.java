package com.coradev.vnwebs.service;

import com.coradev.vnwebs.model.User;

public interface UserService {
    User checkUser(String username, String password);
}
