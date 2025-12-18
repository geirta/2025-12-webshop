package ee.geir.webshop.service;

import ee.geir.webshop.entity.Person;
import ee.geir.webshop.repository.PersonRepository;
import ee.geir.webshop.util.Validations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public void validate(Person person) {
        if (person.getId() != null) {
            throw new RuntimeException("Cannot sign up with ID");
        }
        if (person.getEmail() == null) {
            throw new RuntimeException("Cannot sign up without email");
        }
        if (!Validations.validateEmail(person.getEmail())) {
            throw new RuntimeException("Email is not valid");
        }
        Optional<Person> dbPerson = personRepository.findByEmailIgnoreCase(person.getEmail());
        if (dbPerson.isPresent()) {
            throw new RuntimeException("Email is already in use");
        }
        if (person.getPassword() == null || person.getPassword().isBlank()) {
            throw new RuntimeException("Cannot sign up without password");
        }
        if (person.getPersonalCode() != null && !Validations.validatePersonalCode(person.getPersonalCode())) {
            throw new RuntimeException("Personal code is not valid");
        }
        if (person.getAddress() != null
                && person.getAddress().getZipcode() != null
                && !Validations.validateZipcode(person.getAddress().getZipcode())
        ) {
            throw new RuntimeException("Address zip code is not valid");
        }
    }

}
