package ee.geir.webshop.util;

import com.github.vladislavgoltjajev.personalcode.locale.estonia.EstonianPersonalCodeValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validations {

    private static final EstonianPersonalCodeValidator personalCodeValidator = new EstonianPersonalCodeValidator();

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validateEmail(String email) {
        if (email.isBlank()) {
            return false;
        }
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        if (!matcher.matches()) {
            return false;
        }
        return true;
    }

    public static boolean validateZipcode(String zipCode) {
        if (zipCode.isBlank()) {
            return false;
        }
        if (zipCode.length() != 5) {
            return false;
        }
        return true;
    }

    public static boolean validatePersonalCode(String personalCode) {

        if (personalCode.isBlank()) {
            return false;
        }
        if (!personalCodeValidator.isValid(personalCode)) {
            return false;
        }
        return true;
    }

    public static boolean validatePhone(String phone) {
        if (phone.isBlank()) {
            return false;
        }
        return true;
    }

}
