package kitos.cardwatcher.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
@Tag(name = "Test Controller", description = "For testing Python Flask API integration")
public class TestController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/flask-hello")
    @Operation(summary = "Call Flask root endpoint")
    public ResponseEntity<Map<String, String>> callFlaskHello() {
        String flaskUrl = "http://localhost:8000/";
        
        try {
            String response = restTemplate.getForObject(flaskUrl, String.class);
            return ResponseEntity.ok(Map.of(
                "flaskResponse", response,
                "message", "Successfully called Flask API"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                "error", "Failed to call Flask API",
                "details", e.getMessage()
            ));
        }
    }

    @GetMapping("/flask-message")
    @Operation(summary = "Call Flask message endpoint")
    public ResponseEntity<Map<String, String>> callFlaskMessage() {
        String flaskUrl = "http://localhost:8000/message";
        
        try {
            String response = restTemplate.getForObject(flaskUrl, String.class);
            return ResponseEntity.ok(Map.of(
                "flaskResponse", response,
                "translation", "Timmy smells bad", // 😄
                "message", "Successfully received message from Flask"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                "error", "Failed to call Flask message endpoint",
                "details", e.getMessage()
            ));
        }
    }

    @GetMapping("/test-both-apis")
    @Operation(summary = "Test communication between Spring Boot and Flask")
    public ResponseEntity<Map<String, Object>> testBothApis() {
        try {
            // Call your Flask API
            String helloResponse = restTemplate.getForObject("http://localhost:8000/", String.class);
            String messageResponse = restTemplate.getForObject("http://localhost:8000/message", String.class);
            
            return ResponseEntity.ok(Map.of(
                "springBootMessage", "Hello from Spring Boot!",
                "flaskHelloResponse", helloResponse,
                "flaskMessageResponse", messageResponse,
                "status", "SUCCESS",
                "message", "Both APIs are communicating successfully!"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                "error", "Communication failed",
                "details", "Make sure Flask is running on port 8000: " + e.getMessage()
            ));
        }
    }
}