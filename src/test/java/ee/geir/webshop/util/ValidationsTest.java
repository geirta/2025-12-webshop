package ee.geir.webshop.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ValidationsTest {

    @Test
    void validate_wrong_email() {
        String email = "emagmail.com";
        assertEquals(false, Validations.validateEmail(email));
    }

    @Test
    void validate_blank_email() {
        String email = "";
        assertEquals(false, Validations.validateEmail(email));
    }

    @Test
    void validate_correct_email() {
        String email = "email@gmail.com";
        assertEquals(true, Validations.validateEmail(email));
    }

    @Test
    void validate_wrong_zipcode() {
        String zipCode = "123";
        assertEquals(false, Validations.validateZipcode(zipCode));
    }

    @Test
    void validate_blank_zipcode() {
        String zipCode = "";
        assertEquals(false, Validations.validateZipcode(zipCode));
    }

    @Test
    void validate_correct_zipcode() {
        String zipCode = "12335";
        assertEquals(true, Validations.validateZipcode(zipCode));
    }

    @Test
    void validate_wrong_personalCode() {
        String personalCode = "123123";
        assertEquals(false, Validations.validatePersonalCode(personalCode));
    }

    @Test
    void validate_blank_personalCode() {
        String personalCode = "";
        assertEquals(false, Validations.validatePersonalCode(personalCode));
    }

    @Test
    void validate_correct_personalCode() {
        String personalCode = "43909040262";
        assertEquals(true, Validations.validatePersonalCode(personalCode));
    }

    @Test
    void validate_invalid_phoneNr() {
        String phoneNr = "";
        assertEquals(false, Validations.validatePhone(phoneNr));
    }

    @Test
    void validate_correct_phoneNr() {
        String phoneNr = "5123123";
        assertEquals(true, Validations.validatePhone(phoneNr));
    }
}