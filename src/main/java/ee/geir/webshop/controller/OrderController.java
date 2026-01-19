package ee.geir.webshop.controller;

import ee.geir.webshop.entity.Order;
import ee.geir.webshop.entity.OrderRow;
import ee.geir.webshop.model.OrderPaid;
import ee.geir.webshop.model.ParcelMachine;
import ee.geir.webshop.model.PaymentLink;
import ee.geir.webshop.repository.OrderRepository;
import ee.geir.webshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:1234")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("orders")
    public List<Order> getOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("person-orders")
    public List<Order> getPersonOrders() {
        Long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return orderRepository.findByPerson_Id(id);
    }

    @PostMapping("orders")
    public PaymentLink addOrder(@RequestBody List<OrderRow> orderRows) {
        Long personId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
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
