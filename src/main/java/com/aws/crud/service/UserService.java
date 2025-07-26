package com.aws.crud.service;

import com.aws.crud.entity.User;
import com.aws.crud.exception.ResourceNotFoundException;
import com.aws.crud.exception.DuplicateResourceException;
import com.aws.crud.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }
    
    public User createUser(User user) {
        // Check if email already exists
        userRepository.findByEmail(user.getEmail())
            .ifPresent(u -> {
                throw new DuplicateResourceException("Email already in use: " + user.getEmail());
            });
            
        return userRepository.save(user);
    }
    
    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);
        
        // Check if email is being changed and if the new email already exists
        if (!user.getEmail().equals(userDetails.getEmail())) {
            userRepository.findByEmail(userDetails.getEmail())
                .ifPresent(u -> {
                    throw new DuplicateResourceException("Email already in use: " + userDetails.getEmail());
                });
        }
        
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setBio(userDetails.getBio());
        
        return userRepository.save(user);
    }
    
    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
    
    public List<User> searchUsers(String query) {
        return userRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(query, query);
    }
}
