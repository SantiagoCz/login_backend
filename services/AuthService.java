package project.login.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.login.auth.AuthResponse;
import project.login.auth.LoginRequest;
import project.login.entities.Person;
import project.login.entities.User;
import project.login.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        try {
            // Authenticate the user
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            // Find the user in the database
            User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

            // Get the person associated with the user
            Person person = userRepository.findPersonByUsername(request.getUsername());

            // Generate JWT token
            String token = jwtService.getToken(user);

            // Build AuthResponse
            AuthResponse authResponse = AuthResponse.builder()
                    .token(token)
                    .role(user.getRole())
                    .person(person)
                    .build();

            return authResponse;
        } catch (AuthenticationException e) {
            // Handle authentication exception
            System.out.println("Authentication error: " + e.getMessage());
            throw new RuntimeException("Error authenticating the user", e);
        }
    }
}

