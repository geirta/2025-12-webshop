package ee.geir.webshop.dto;

import ee.geir.webshop.entity.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonUpdateDto {
    private String firstName;
    private String lastName;
    private String email;
    private String personalCode;
    private String phone;
    private Address address;
}
