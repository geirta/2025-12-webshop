package ee.geir.webshop.repository;

import ee.geir.webshop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByPerson_Id(Long id);
}
