package com.example.libraryapp.service;

import com.example.libraryapp.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Integer id);
}
