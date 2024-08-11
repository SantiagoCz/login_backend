package project.login.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.login.auth.AuthResponse;
import project.login.auth.LoginRequest;
import project.login.entities.Person;
import project.login.services.AuthService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            AuthResponse authResponse = authService.login(request);

            // Obtain additional data
            String userRole = String.valueOf(authResponse.getRole());
            Person person = authResponse.getPerson();

            // Create a response object containing both the token and user data
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("token", authResponse.getToken());
            responseData.put("role", userRole);
            responseData.put("person", person);
            // ADAPT AS NEEDED...

            // Return a successful response with user data
            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            String errorMessage = "Login error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

}

