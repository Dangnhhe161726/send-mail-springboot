package com.springboot.demo.service;

import com.springboot.demo.entity.User;

public interface UserService {
    User saveUser(User user);
    Boolean verifyToken(String token);
}
