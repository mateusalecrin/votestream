package com.votestream.api.service;

import com.votestream.api.exception.ConflictException;
import com.votestream.api.exception.ResourceNotFoundException;
import com.votestream.api.model.User;
import com.votestream.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User create(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ConflictException("Email already in use");
        }
        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}