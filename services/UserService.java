package project.login.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.login.entities.Role;
import project.login.entities.User;
import project.login.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

// INJECTIONS:

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

//

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

// OTHER METHODS:

    public User buildUser(User user) {
        return User.builder()
                .username(user.getPerson().getIdNumber()) // Assign IdNumber as username
                .password(passwordEncoder.encode(user.getPerson().getIdNumber()))
                .role(Role.PROFESIONAL)
                .person(user.getPerson())
                .build();
    }

}
