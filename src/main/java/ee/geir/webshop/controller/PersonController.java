package ee.geir.webshop.controller;

import ee.geir.webshop.dto.PersonLoginDto;
import ee.geir.webshop.dto.PersonPublicDto;
import ee.geir.webshop.dto.PersonUpdateRecordDto;
import ee.geir.webshop.entity.Person;
import ee.geir.webshop.entity.PersonRole;
import ee.geir.webshop.model.AuthToken;
import ee.geir.webshop.repository.PersonRepository;
import ee.geir.webshop.security.JwtService;
import ee.geir.webshop.service.PersonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// @CrossOrigin(origins = "http://localhost:1234")   -- pole enam vaja, sai paika pandud SecurityConfigis
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonService personService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private JwtService jwtService;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @GetMapping("persons")
    public List<Person> getPersons() {
        return personRepository.findAllByOrderByIdAsc();
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

    // 1. kord v6tab, siis paneb cache-i, aga v6tab andmebaasist
    // 2. kord v6tab, siis v6tab cache-st
    @Cacheable(value = "userCache", key = "#id")
    @GetMapping("persons/{id}")
    public Person getPersonDetails(@PathVariable Long id) {
        return personRepository.findById(id).orElseThrow();
    }

    @PostMapping("signup")
    public Person signup(@RequestBody Person person) {
        personService.validate(person);
// PÄRIS RAKENDUSES: person.setRole(PersonRole.CUSTOMER);
        if (person.getRole() == null) {
            person.setRole(PersonRole.CUSTOMER);
        }
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        return personRepository.save(person);
    }

    @PostMapping("login")
    public AuthToken login(@RequestBody PersonLoginDto personLoginDto) {
        Person dbPerson = personRepository.findByEmailIgnoreCase(personLoginDto.getEmail());
        if (dbPerson == null) {
            throw new RuntimeException("Email not found");
        }
//        if (!dbPerson.getPassword().equals(personLoginDto.getPassword())) {
        if (!passwordEncoder.matches(personLoginDto.getPassword(), dbPerson.getPassword())) {
            throw new RuntimeException("Password not correct");
        }

        return jwtService.getToken(dbPerson);
    }

    // PATCH:  soovituslik kasutada 1 välja muutmise puhul
    // PATCH:  admin -> customer -> admin
    // CachePut - iga p2ring updateb cache-i
    @CachePut(value = "userCache", key = "#result.id")
    @PutMapping("persons")
    public Person updatePerson(@RequestBody PersonUpdateRecordDto dto) {
        Long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
// PÄRIS RAKENDUSES: iseenda rolli ei tohiks muuta
        return personService.updatePerson(id, dto);
    }

    @CacheEvict(value = "userCache", key = "#personId")
    @PatchMapping("change-admin")
    public List<Person> changeAdmin(@RequestParam Long personId) {
        Person dbPerson = personRepository.findById(personId).orElseThrow();
        if (dbPerson.getRole() == PersonRole.ADMIN) {
            dbPerson.setRole(PersonRole.CUSTOMER);
        } else {
            dbPerson.setRole(PersonRole.ADMIN);
        }
        personRepository.save(dbPerson);
        return personRepository.findAllByOrderByIdAsc();
    }
}
