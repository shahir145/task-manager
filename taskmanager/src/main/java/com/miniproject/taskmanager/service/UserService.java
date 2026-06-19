package com.miniproject.taskmanager.service;

import com.miniproject.taskmanager.dto.LoginRequest;
import com.miniproject.taskmanager.dto.RegisterRequest;
import com.miniproject.taskmanager.exceptions.UsernameExistsException;
import com.miniproject.taskmanager.model.User;
import com.miniproject.taskmanager.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(RegisterRequest req){
        if (userRepository.existsByUsername(req.username())) {
            throw new UsernameExistsException(req.username());
        }

        User u = new User();
        u.setUsername(req.username());
        u.setPassword(passwordEncoder.encode(req.password()));
        return userRepository.save(u);
    }

    public User loadUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }


}