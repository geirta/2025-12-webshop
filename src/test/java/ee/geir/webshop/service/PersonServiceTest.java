package ee.geir.webshop.service;

import ee.geir.webshop.entity.Address;
import ee.geir.webshop.entity.Person;
import ee.geir.webshop.repository.PersonRepository;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    // given_when_then


    // TEST: CANT ADD A NEW PERSON WITH ID
    @Test
    void givenPersonCantBeAdded_whenIdExists_thenExceptionIsThrown() {
        Person p1 = new Person();
        p1.setId(1L);
        p1.setFirstName("Geir");
        p1.setLastName("Tag");
        p1.setEmail("g.t@gmail.com");
        p1.setPassword("pw");
        p1.setPersonalCode("43909040262");
        p1.setPhone("555123123");
        p1.setAddress(new Address());

        String msg = assertThrows(RuntimeException.class, () -> personService.validate(p1)).getMessage();
        assertEquals("Cannot sign up with ID", msg);
    }

    // TEST: EMAIL ALREADY EXISTS
    @Test
    void givenPersonCantBeAdded_whenEmailAlreadyExists_thenExceptionIsThrown() {
        mockSavePersonToDb();
        Person person = getPerson("Geir", "Tag", "g.t@gmail.com", "", "43909040262",
                "55512312", new Address());

        String msg = assertThrows(RuntimeException.class, () -> personService.validate(person)).getMessage();
        assertEquals("Email is already in use", msg);
    }

    // TEST: EMAIL IS NOT VALID
    @Test
    void givenPersonCantBeAdded_whenEmailIsNotValid_thenExceptionIsThrown() {
        Person person = getPerson("Geir", "Tag", "g.tgmail.com", "pw", "43909040262",
                "55512312", new Address());

        String msg = assertThrows(RuntimeException.class, () -> personService.validate(person)).getMessage();
        assertEquals("Email is not valid", msg);
    }

    // TEST: password.length > 0
    @Test
    void givenPersonCantBeAdded_whenPasswordIsEmpty_thenExceptionIsThrown() {
        Person person = getPerson("Geir", "Tag", "g.t@gmail.com", "", "43909040262",
                "55512312", new Address());

        String msg = assertThrows(RuntimeException.class, () -> personService.validate(person)).getMessage();
        assertEquals("Cannot sign up without password", msg);
    }

    // TEST: personal code not valid
    @Test
    void givenPersonCantBeAdded_whenPersonalCodeIsNotValid_thenExceptionIsThrown() {
        Person person = getPerson("Geir", "Tag", "g.t@gmail.com", "pw", "11111111111",
                "55512312", new Address());

        String msg = assertThrows(RuntimeException.class, () -> personService.validate(person)).getMessage();
        assertEquals("Personal code is not valid", msg);
    }

    // TEST: address not valid
    @Test
    void givenPersonCantBeAdded_whenAddressNotValid_thenExceptionIsThrown() {
        Address address = new Address();
        address.setZipcode("invalid");
        Person person = getPerson("Geir", "Tag", "g.t@gmail.com", "pw", "43909040262",
                "55512312", address);

        String msg = assertThrows(RuntimeException.class, () -> personService.validate(person)).getMessage();
        assertEquals("Address zip code is not valid", msg);
    }


    private static Person getPerson(String name, String lastName, String email, String password, String personalcode,
                                    String phoneNr, Address address) {
        Person p = new Person();
        p.setFirstName(name);
        p.setLastName(lastName);
        p.setEmail(email);
        p.setPassword(password);
        p.setPersonalCode(personalcode);
        p.setPhone(phoneNr);
        p.setAddress(address);
        return p;
    }

    private void mockSavePersonToDb() {
        Person p1 = new Person();
        p1.setFirstName("Geir");
        p1.setLastName("Tag");
        p1.setEmail("g.t@gmail.com");
        p1.setPassword("pw");
        p1.setPersonalCode("43909040262");
        p1.setPhone("555123123");
        p1.setAddress(new Address());
        when(personRepository.findByEmailIgnoreCase(p1.getEmail())).thenReturn(Optional.of(p1));
    }
}