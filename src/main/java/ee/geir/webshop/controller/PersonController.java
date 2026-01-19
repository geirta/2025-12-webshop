package ee.geir.webshop.controller;

import ee.geir.webshop.dto.PersonLoginDto;
import ee.geir.webshop.dto.PersonPublicDto;
import ee.geir.webshop.dto.PersonUpdateDto;
import ee.geir.webshop.dto.PersonUpdateRecordDto;
import ee.geir.webshop.entity.Person;
import ee.geir.webshop.entity.PersonRole;
import ee.geir.webshop.model.AuthToken;
import ee.geir.webshop.repository.PersonRepository;
import ee.geir.webshop.security.JwtService;
import ee.geir.webshop.service.PersonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:1234")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonService personService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private JwtService jwtService;

    @GetMapping("persons")
    public List<Person> getPersons() {
        return personRepository.findAll();
    }

    @GetMapping("persons-public")
    public List<PersonPublicDto> getPersonsPublic() {
//        List<Person> personFromDb = personRepository.findAll();
//        List<PersonPublicDto> personsDto = new ArrayList<>();
//        for (Person person : personFromDb) {
//            PersonPublicDto personPublicDto = new PersonPublicDto();
//            personPublicDto.setFirstName(person.getFirstName());
//            personPublicDto.setLastName(person.getLastName());
//            personsDto.add(personPublicDto);
//        }
//        return personsDto;

        // ülevaloleva teeb ModelMapper lihtsamalt ära -->

        return List.of(mapper.map(personRepository.findAll(), PersonPublicDto[].class));
    }


    @GetMapping("profile")
    public Person getPersonDetails() {
        Long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return personRepository.findById(id).orElseThrow();
    }

    @PostMapping("signup")
    public Person signup(@RequestBody Person person) {
        personService.validate(person);
        // TODO: !!!! person.setRole(PersonRole.CUSTOMER);
        return personRepository.save(person);
    }

    @PostMapping("login")
    public AuthToken login(@RequestBody PersonLoginDto personLoginDto) {
        Person dbPerson = personRepository.findByEmailIgnoreCase(personLoginDto.getEmail());
        if (dbPerson == null) {
            throw new RuntimeException("Email not found");
        }
        if (!dbPerson.getPassword().equals(personLoginDto.getPassword())) {
            throw new RuntimeException("Password not correct");
        }

        return jwtService.getToken(dbPerson);
    }

    // PATCH:  soovituslik kasutada 1 välja muutmise puhul
    // PATCH:  admin -> customer -> admin
    @PutMapping("persons")
    public Person updatePerson(@RequestBody PersonUpdateRecordDto dto) {
        Long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        // TODO: !!!! iseenda rolli ei tohiks muuta
        return personService.updatePerson(id, dto);
    }

}
