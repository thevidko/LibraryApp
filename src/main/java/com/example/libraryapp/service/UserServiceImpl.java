package com.example.libraryapp.service;

import com.example.libraryapp.model.User;
import com.example.libraryapp.repository.UserRepository;
import com.example.libraryapp.security.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Integer id) {
        Optional<User> user = userRepository.findById(id.longValue());
        return user.orElse(null);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> searchUsers(String query, String filter) {
        switch (filter) {
            case "name":
                return userRepository.findByNameContainingIgnoreCase(query);
            case "surname":
                return userRepository.findBySurnameContainingIgnoreCase(query);
            case "email":
                return userRepository.findByUsernameContainingIgnoreCase(query);
            default:
                return getAllUsers();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserDetailsImpl(user);

    }
}
