package kitos.cardwatcher.controllers.rest;

import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import kitos.cardwatcher.dtos.requests.CreateUserRequest;
import kitos.cardwatcher.dtos.responses.UserResponse;
import kitos.cardwatcher.dtos.shared.UserDTO;
import kitos.cardwatcher.entities.User;
import kitos.cardwatcher.services.UserService;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "User management operations")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "Get all users")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers().stream()
            .map(UserDTO::new)
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID")
    public ResponseEntity<UserDTO> getUserById(
            @PathVariable("id") @Parameter(description = "User ID", example = "1") Long id) {
        return userService.getUserById(id)
            .map(user -> ResponseEntity.ok(new UserDTO(user)))
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/username/{username}")
    @Operation(summary = "Get user by username")
    public ResponseEntity<UserDTO> getUserByUsername(
            @PathVariable("username") @Parameter(description = "Username", example = "john_doe") String username) {
        return userService.getUserByUsername(username)
            .map(user -> ResponseEntity.ok(new UserDTO(user)))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new user")
    public ResponseEntity<UserResponse> createUser(
            @RequestBody @Parameter(description = "User creation data") CreateUserRequest createUserRequest) {
        User user = new User();
        user.setUsername(createUserRequest.getUsername());
        
        User savedUser = userService.createUser(user);
        UserResponse response = new UserResponse(savedUser);
        
        return ResponseEntity
            .created(URI.create("/api/users/" + savedUser.getId()))
            .body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a user")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable("id") @Parameter(description = "User ID", example = "1") Long id,
            @RequestBody @Parameter(description = "User update data") CreateUserRequest updateUserRequest) {
        try {
            User user = new User();
            user.setUsername(updateUserRequest.getUsername());
            
            User updatedUser = userService.updateUser(id, user);
            UserResponse response = new UserResponse(updatedUser);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user")
    public ResponseEntity<Void> deleteUser(
            @PathVariable("id") @Parameter(description = "User ID", example = "1") Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}