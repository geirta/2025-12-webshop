package ee.geir.webshop.controller;

import ee.geir.webshop.entity.Product;
import ee.geir.webshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:1234")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("products")
    public List<Product> getProducts() {
        return productRepository.findByActiveTrueOrderByIdAsc();
    }

    @GetMapping("admin-products")
    public List<Product> getAdminProducts() {
        return productRepository.findAll();
    }

    @PostMapping("products")
    public List<Product> addProduct(@RequestBody Product product) {
        if (product.getId() != null) {
            throw new RuntimeException("Cannot add product with this id");
        }
        productRepository.save(product);
        return productRepository.findAll();
    }

    @DeleteMapping("products/{id}")
    public List<Product> deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return productRepository.findAll();
    }

    @GetMapping("products/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productRepository.findById(id).get();
        //return productRepository.findById(id).orElseThrow();    ALTERNATIIV
    }

    // @RequestParam -->  kui on 2 või rohkem parameetrit
    //               -->  kui m6ni on nullable ehk pole vajalik saata

    // @PathVariable on selgem --> t2pselt 1 muutuja, mis on alati vajalik


    @PutMapping("products")
    public List<Product> updateProduct(@RequestBody Product product) {
        if (product.getId() == null) {
            throw new RuntimeException("Cannot update product without id");
        }
        productRepository.save(product);
        return productRepository.findAll();
    }
}
