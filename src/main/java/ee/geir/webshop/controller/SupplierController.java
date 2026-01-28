package ee.geir.webshop.controller;

import ee.geir.webshop.model.Supplier1Product;
import ee.geir.webshop.model.Supplier2Product;
import ee.geir.webshop.model.Supplier3Product;
import ee.geir.webshop.model.SupplierProduct;
import ee.geir.webshop.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// supplier -> tarnija
// v6tame hankija API endpoindilt enda rakendusse k6ik tooted

@RestController
// @CrossOrigin(origins = "http://localhost:1234")   -- pole enam vaja, sai paika pandud SecurityConfigis
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping("supplier1")
    public List<Supplier1Product> getProductsFromSupplier1() {
        return supplierService.getProductsFromSupplier1();
    }

    @GetMapping("supplier2")
    public List<Supplier2Product> getProductsFromSupplier2() {
        return supplierService.getProductsFromSupplier2();
    }

    @GetMapping("supplier3")
    public List<Supplier3Product> getProductsFromSupplier3() {
        return supplierService.getProductsFromSupplier3();
    }

    @GetMapping("supplier-products")
    public List<SupplierProduct> getProductsFromSupplier() {
        return supplierService.getProductsFromSupplier();
    }
}
