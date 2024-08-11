package project.login.components;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import project.login.entities.Person;
import project.login.entities.Role;
import project.login.entities.User;
import project.login.services.PersonService;
import project.login.services.UserService;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class DataLoaderUser {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PersonService personService;
    @Autowired
    private UserService userService;

    @PostConstruct
    public void loadData() {
        createUser();
    }

    public void createUser() {
        String idNumber = "12345678";

        Optional<Person> existingPersonOptional = personService.findPersonByDniOptional(idNumber);
        if (existingPersonOptional.isPresent()) {
            System.out.println("A person with the provided Id Number already exists.");
            return;
        }

        Person person = Person.builder()
                .idNumber(idNumber)
                .firstName("abc")
                .lastName("def")
                .birthDate(LocalDate.of(1990, 1, 1))
                .phoneNumber("0123456789")
                .build();

        // Create and save the user
        User user = User.builder()
                .username(idNumber)
                .password(passwordEncoder.encode("1234"))
                .role(Role.ADMIN)
                .person(person)
                .build();

        try {
            personService.createPerson(person);
            userService.createUser(user);
            System.out.println("User automatically created: " + person.getFirstName());
        } catch (Exception e) {
            System.out.println("Error creating the user: " + e.getMessage());
        }
    }
}


