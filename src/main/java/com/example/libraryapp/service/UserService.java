package com.example.libraryapp.service;

import com.example.libraryapp.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getAllUsers();
    User getUserById(Integer id);

    User findByUsername(String username);

    void save(User user);
    List<User> searchUsers(String query, String filter);
    List<User> getUnverifiedUsers();

}
