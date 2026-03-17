package kitos.cardwatcher.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import kitos.cardwatcher.dtos.requests.LoginRequestDTO;
import kitos.cardwatcher.dtos.requests.RegisterRequestDTO;
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
	public ResponseEntity<?> registerUser(@RequestBody RegisterRequestDTO request) {
		if (authService.registerUser(request.getUsername(), request.getPassword())) {
			return ResponseEntity.ok().body(Map.of("message", "User registered successfully"));
		} else {
			return ResponseEntity.badRequest().body(Map.of("error", "Username already exists"));
		}
	}

	@PostMapping("/login")
	@Operation(summary = "Login user")
	public ResponseEntity<?> loginUser(@RequestBody LoginRequestDTO request) {
		if (authService.validateCredentials(request.getUsername(), request.getPassword())) {
			String token = authService.generateToken(request.getUsername());
			return ResponseEntity.ok().body(Map.of("token", token));
		} else {
			return ResponseEntity.badRequest().body(Map.of("error", "Invalid credentials"));
		}
	}
}
