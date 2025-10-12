package kitos.cardwatcher.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import kitos.cardwatcher.services.AuthService;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "User authentication operations")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<?> registerUser(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        
        if (authService.registerUser(username, password)) {
            return ResponseEntity.ok().body(Map.of("message", "User registered successfully"));
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", "Username already exists"));
        }
    }
    
    @PostMapping("/login")
    @Operation(summary = "Login user")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        
        if (authService.validateCredentials(username, password)) {
            // Aqui vais gerar o JWT token depois
            return ResponseEntity.ok().body(Map.of("message", "Login successful", "userId", authService.getUserIdFromUsername(username)));
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid credentials"));
        }
    }
}
