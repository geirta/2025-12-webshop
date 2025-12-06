package ee.geir.webshop.repository;

import ee.geir.webshop.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findByEmailIgnoreCase(String email);
}
