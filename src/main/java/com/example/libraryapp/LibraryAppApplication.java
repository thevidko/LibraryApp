package com.example.libraryapp;

import com.example.libraryapp.model.User;
import com.example.libraryapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class LibraryAppApplication {

    private UserService userService;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public LibraryAppApplication(UserService userService, PasswordEncoder passwordEncoder){
        this.userService =userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner demo() {
        return (args) -> {
            addUser("admin", "heslo", "ADMIN");
            addUser("user", "heslo", "USER");
        };
    }

    private void addUser(String username, String password, String role) {
        if (userService.findByUsername(username) == null) {
            User user = new User();
            user.setName("testing");
            user.setSurname("testing");
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setRole(role);
            userService.save(user);
        }
    }
    public static void main(String[] args) {
        SpringApplication.run(LibraryAppApplication.class, args);
    }

}
