package com.aws.crud.controller;

import com.aws.crud.entity.User;
import com.aws.crud.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "APIs for managing users")
@CrossOrigin(origins = "*")
public class UserController {
    
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieve a list of all users")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieve a specific user by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<User> getUserById(
            @Parameter(description = "ID of the user to be retrieved") @PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new user", description = "Create a new user with the provided details")
    @ApiResponse(responseCode = "201", description = "User created successfully")
    public User createUser(
            @Parameter(description = "User object to be created") @Valid @RequestBody User user) {
        return userService.createUser(user);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update user", description = "Update an existing user's details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User updated successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public User updateUser(
            @Parameter(description = "ID of the user to be updated") @PathVariable Long id,
            @Parameter(description = "Updated user object") @Valid @RequestBody User userDetails) {
        return userService.updateUser(id, userDetails);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete user", description = "Delete a user by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "User deleted successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public void deleteUser(
            @Parameter(description = "ID of the user to be deleted") @PathVariable Long id) {
        userService.deleteUser(id);
    }
    
    @GetMapping("/search")
    @Operation(summary = "Search users", description = "Search users by name or email")
    @ApiResponse(responseCode = "200", description = "Search completed successfully")
    public List<User> searchUsers(
            @Parameter(description = "Search query (name or email)") @RequestParam String query) {
        return userService.searchUsers(query);
    }
}
