package ee.geir.webshop.service;

import ee.geir.webshop.entity.*;
import ee.geir.webshop.model.*;
import ee.geir.webshop.repository.OrderRepository;
import ee.geir.webshop.repository.PersonRepository;
import ee.geir.webshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CacheService cacheService;

    /*
        VALUE t6mbab application.properties failist muutujad sisse
     */
    @Value("${everypay.base-url}")
    private String everyPayBaseUrl;

    @Value("${everypay.username}")
    private String everyPayUsername;

    @Value("${everypay.password}")
    private String everyPayPassword;

    @Value("${everypay.customer-url}")
    private String everyPayCustomerUrl;

    @Value("${everypay.order-prefix}")
    private String everyPayOrderPrefix;


    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<ParcelMachine> getParcelMachines(@RequestParam String country) {
        String url = "https://www.omniva.ee/locations.json";
        ParcelMachine[] response =  restTemplate.exchange(url, HttpMethod.GET, null, ParcelMachine[].class).getBody();
        return Arrays.stream(response).filter(p -> p.a0_NAME.equals(country.toUpperCase())).toList();
    }

    public PaymentLink pay(@RequestParam Long personId, @RequestBody List<OrderRow> orderRows) throws ExecutionException { // TODO: personId authist
        Order order = saveOrder(personId, orderRows);
        // 1. ID saadame maksesse
        // 2. Enne makset v6iks salvestada andmebaasi maksmata kujul terve tellimuse
        return getPaymentLink(order.getId(), order.getTotal());
    }

    // ?order_reference=mn3124ffdau14&payment_reference=23c984e66fa5e9435858f362f3921313f355f6ad7293358cb42df47efe1988f3
    // ?order_reference=mn3124ffdau15&payment_reference=81ee5d101733544e24c20697ca5954e57961baccb8aa24da3b8929eda79a4a11
    private PaymentLink getPaymentLink(Long id, double total) {
        String url = everyPayBaseUrl + "/payments/oneoff";

        EveryPayBody body = new EveryPayBody();
        body.setAccount_name("EUR3D1"); // apollo group. apollo kino, blender, postimees, duo
        body.setNonce(String.valueOf(System.currentTimeMillis())); // turvaelemnt, et ei l2heks 2x identne p2ring
        body.setTimestamp(ZonedDateTime.now().toString()); // turvaelement . praegune kellaaeg +- 5 minutit
        body.setAmount(total);
        body.setOrder_reference(everyPayOrderPrefix + id); // tellimuse id
        body.setCustomer_url(everyPayCustomerUrl); // kuhu meid tagasi suunatakse
        body.setApi_username(everyPayUsername); // kasutajanimi, mis peab yhtima headeris oleva kasutajanimega

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(everyPayUsername, everyPayPassword);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity entity = new HttpEntity(body, headers);

        EveryPayResponse response = restTemplate.exchange(url, HttpMethod.POST, entity, EveryPayResponse.class).getBody();
        if (response == null) {
            throw new RuntimeException("Couldn't get every-pay response");
        }
        PaymentLink paymentLink = new PaymentLink();
        paymentLink.setLink(response.getPayment_link());
        return paymentLink;
    }

    private Order saveOrder(Long personId, List<OrderRow> orderRows) throws ExecutionException {
        Order order = new Order();
        Person person = personRepository.findById(personId).orElseThrow();
        order.setPerson(person);
        order.setOrderRows(orderRows);
        double sum = 0;
        for (OrderRow orderRow : orderRows) {
            // Product dbProduct = productRepository.findById(orderRow.getProduct().getId()).orElseThrow();   // ILMA CACHETA
            Product dbProduct = cacheService.getProduct(orderRow.getProduct().getId());
            sum += dbProduct.getPrice() * orderRow.getQuantity();
        }
        order.setTotal(sum);
        order.setPaymentStatus(PaymentStatus.initial);
        return orderRepository.save(order);
    }

    // SETTLED:
    // "https://igw-demo.every-pay.com/api/v4/payments/23c984e66fa5e9435858f362f3921313f355f6ad7293358cb42df47efe1988f3?api_username=e36eb40f5ec87fa2&detailed=false";
    // FAILED:
    // "https://igw-demo.every-pay.com/api/v4/payments/81ee5d101733544e24c20697ca5954e57961baccb8aa24da3b8929eda79a4a11?api_username=e36eb40f5ec87fa2&detailed=false";
    public OrderPaid checkPayment(String orderReference, String paymentReference) {
        String url = everyPayBaseUrl + "/payments/" + paymentReference
                + "?api_username=" + everyPayUsername + "&detailed=false";

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(everyPayUsername, everyPayPassword);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity entity = new HttpEntity(null, headers);

        EveryPayCheck response = restTemplate.exchange(url, HttpMethod.GET, entity, EveryPayCheck.class).getBody();
        if (response == null) {
            throw new RuntimeException("Couldn't get every-pay response");
        }
        if (!response.getOrder_reference().equals(orderReference)) {
            throw new RuntimeException("Order reference does not match");
        }
        Order order = orderRepository.findById(Long.valueOf(orderReference.replace(everyPayOrderPrefix, ""))).orElseThrow();
        order.setPaymentStatus(PaymentStatus.valueOf(response.getPayment_state()));
        orderRepository.save(order);

        OrderPaid orderPaid = new OrderPaid();
        orderPaid.setPaid(response.getPayment_state().equals("settled"));
        return orderPaid;
    }
}
