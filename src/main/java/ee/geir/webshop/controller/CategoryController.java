package ee.geir.webshop.controller;

import ee.geir.webshop.entity.Category;
import ee.geir.webshop.repository.CategoryRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@CrossOrigin(origins = "http://localhost:1234")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("categories")
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.status(200).body(categoryRepository.findAll());

    }

    @PostMapping("categories")
    public ResponseEntity<List<Category>> addCategory(@RequestBody Category category) {
        System.out.println("1. Adding Category..");
        log.info("2. Adding Category...");
        if (category.getId() != null) {
            throw new RuntimeException("Cannot add category with this id");
        }
        categoryRepository.save(category);
        return ResponseEntity.status(201).body(categoryRepository.findAll());
    }

    @DeleteMapping("categories/{id}")
    public List<Category> deleteCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return categoryRepository.findAll();
    }

}
