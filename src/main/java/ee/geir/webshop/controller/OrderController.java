package ee.geir.webshop.controller;

import ee.geir.webshop.entity.Order;
import ee.geir.webshop.entity.OrderRow;
import ee.geir.webshop.entity.Person;
import ee.geir.webshop.entity.Product;
import ee.geir.webshop.model.OrderPaid;
import ee.geir.webshop.model.ParcelMachine;
import ee.geir.webshop.model.PaymentLink;
import ee.geir.webshop.model.Supplier3Response;
import ee.geir.webshop.repository.OrderRepository;
import ee.geir.webshop.repository.PersonRepository;
import ee.geir.webshop.repository.ProductRepository;
import ee.geir.webshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:1234")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("orders")
    public List<Order> getOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping("orders")
    public PaymentLink addOrder(@RequestParam Long personId, @RequestBody List<OrderRow> orderRows) { // TODO: personId authist
        return orderService.pay(personId, orderRows);
    }

    // localhost:8099/parcelmachines?country=ee
    @GetMapping("parcelmachines")
    public List<ParcelMachine> getParcelMachines(@RequestParam String country) {
        return orderService.getParcelMachines(country);
    }

    @GetMapping("check-payment")
    public OrderPaid checkPayment(@RequestParam String orderReference, String paymentReference) {
        return orderService.checkPayment(orderReference, paymentReference);
    }

}
