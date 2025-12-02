package ee.geir.webshop.repository;


import ee.geir.webshop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;


// CrudRepository ---> minimaalne kogus funktsionaale
// PagingAndSortingRepository ---> lk kaupa minemine + sortimine
// JpaRepository ---> max kogus funktsionaale

public interface ProductRepository extends JpaRepository<Product, Long> {

}
