package project.login.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.login.entities.Person;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    Person findPersonaByIdNumber(String idNumber);
    Optional<Person> findByIdNumber(String idNumber);
}
