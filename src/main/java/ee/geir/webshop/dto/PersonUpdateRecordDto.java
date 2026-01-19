package ee.geir.webshop.dto;

import ee.geir.webshop.entity.Address;

public record PersonUpdateRecordDto(
        String firstName,
        String lastName,
        String email,
        String personalCode,
        String phone,
        Address address
) {}
