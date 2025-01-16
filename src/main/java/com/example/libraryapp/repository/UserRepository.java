package com.example.libraryapp.repository;

import com.example.libraryapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    List<User> findByNameContainingIgnoreCase(String name);
    List<User> findBySurnameContainingIgnoreCase(String surname);
    List<User> findByUsernameContainingIgnoreCase(String username);
    List<User> findByEnabledFalse();
}
