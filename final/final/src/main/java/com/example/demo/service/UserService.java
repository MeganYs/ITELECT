package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    


    public boolean authenticate(String username, String password) {
        System.out.println("Login attempt -> Username: [" + username + "], Password: [" + password + "]");

        Optional<User> userOpt = userRepository.findByUsernameIgnoreCase(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            System.out.println("Found in DB -> Username: [" + user.getUsername() + "], Password: [" + user.getPassword() + "]");

            boolean isMatch = user.getPassword().equals(password);
            System.out.println("Password match: " + isMatch);
            return isMatch;
        } else {
            System.out.println("User not found.");
            return false;
        }
    }

}
