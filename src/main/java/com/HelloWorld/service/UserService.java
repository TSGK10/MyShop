package com.HelloWorld.service;

import com.HelloWorld.mapper.UserMapper;
import com.HelloWorld.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User authenticate(Integer userId, String password) {
        User user = userMapper.findById(userId);
        if (user != null && user.getPassword() != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
