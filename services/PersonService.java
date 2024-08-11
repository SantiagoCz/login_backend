package project.login.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.login.entities.Person;
import project.login.repositories.PersonRepository;

import java.util.Optional;

@Service
public class PersonService {

// INJECTIONS:

    @Autowired
    private PersonRepository personRepository;

//
    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    public Person findPersonByDni(String dni) {
        return personRepository.findPersonaByIdNumber(dni);
    }

    public Optional<Person> findPersonByDniOptional(String dni) {
        return personRepository.findByIdNumber(dni);
    }

}
