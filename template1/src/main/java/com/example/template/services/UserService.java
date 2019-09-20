package com.example.template.services;

import com.example.template.entities.User;

import java.util.List;

public interface UserService {

    List<User> getUsers();

    User saveUser(User user);
}
