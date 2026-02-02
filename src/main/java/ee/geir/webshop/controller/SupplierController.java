package ee.geir.webshop.controller;

import ee.geir.webshop.model.Supplier1Product;
import ee.geir.webshop.model.Supplier2Product;
import ee.geir.webshop.model.Supplier3Product;
import ee.geir.webshop.model.SupplierProduct;
import ee.geir.webshop.service.MailService;
import ee.geir.webshop.service.SupplierService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
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

    @Autowired
    private MailService mailService; // TODO: eraldi controller

    @GetMapping("supplier1")
    public List<Supplier1Product> getProductsFromSupplier1() {
        mailService.sendPlainText("geir.tagapere@gmail.com", "Tere", "Sisu");
        return supplierService.getProductsFromSupplier1();
    }

    @GetMapping("supplier2")
    public List<Supplier2Product> getProductsFromSupplier2() throws MessagingException {
        mailService.sendHtml("geir.tagapere@gmail.com", "Tere", "<h1>Sisu</h1><button>NUPP</button>");
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
