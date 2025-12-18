package ee.geir.webshop.service;

import ee.geir.webshop.dto.PersonUpdateDto;
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

    public Person updatePerson(Long id, PersonUpdateDto dto) {
        Person person = personRepository.findById(id).orElseThrow(() -> new RuntimeException("Person not found"));
        if (dto.getFirstName() != null) person.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) person.setLastName(dto.getLastName());
        if (dto.getEmail() != null) person.setEmail(dto.getEmail());
        if (dto.getPersonalCode() != null) person.setPersonalCode(dto.getPersonalCode());
        if (dto.getPhone() != null) person.setPhone(dto.getPhone());

        return personRepository.save(person);
    }

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
