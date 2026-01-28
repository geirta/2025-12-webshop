package ee.geir.webshop.controller;

import ee.geir.webshop.entity.Product;
import ee.geir.webshop.repository.ProductRepository;
import ee.geir.webshop.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
//@CrossOrigin(origins = "http://localhost:1234")  -- pole enam vaja, sai paika pandud SecurityConfigis
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CacheService cacheService;


    // muutsin List -> Page
    //BASE URL      endpoint
    // localhost:8099/products?page=0&size=20&sort=id,asc
    @GetMapping("products")
    public Page<Product> getProducts(Pageable pageable) {
        return productRepository.findByActiveTrue(pageable);
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
        cacheService.deleteFromCache(id);
        return productRepository.findAll();
    }

    @GetMapping("products/{id}")
    public Product getProduct(@PathVariable Long id) throws ExecutionException {
        //return productRepository.findById(id).get();      ALTERNATIIV
        //return productRepository.findById(id).orElseThrow();
        return cacheService.getProduct(id);     // CACHE NÄIDE
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
        cacheService.updateCache(product.getId(),  product);
        return productRepository.findAll();
    }
}
