package ee.geir.webshop.controller;

import ee.geir.webshop.entity.Person;
import ee.geir.webshop.repository.PersonRepository;
import ee.geir.webshop.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonService personService;

    @GetMapping("persons")
    public List<Person> getPersons() {
        return personRepository.findAll();
    }

    @GetMapping("persons/{id}")
    public Person getPerson(@PathVariable Long id) {
        return personRepository.findById(id).get();
    }

    @PostMapping("signup")
    public Person signup(@RequestBody Person person) {
        personService.validate(person);
        return personRepository.save(person);
    }

}
