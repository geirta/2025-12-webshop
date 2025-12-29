package ee.geir.webshop.repository;


import ee.geir.webshop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


// CrudRepository ---> minimaalne kogus funktsionaale
// PagingAndSortingRepository ---> lk kaupa minemine + sortimine
// JpaRepository ---> max kogus funktsionaale

@Repository //pole vaja annoteerida, tuleb automaatselt
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByActiveTrueOrderByIdAsc();
}
