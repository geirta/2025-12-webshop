package ee.geir.webshop.service;

import ee.geir.webshop.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
public class SupplierService {

    @Autowired
    RestTemplate restTemplate;

    public List<Supplier1Product> getProductsFromSupplier1() {
        String url = "https://fakestoreapi.com/products";
        Supplier1Product[] response =  restTemplate.exchange(url, HttpMethod.GET, null, Supplier1Product[].class).getBody();
        return Arrays.stream(response).filter(e -> e.getRating().getRate() >= 4.0).toList();
    }

    public List<Supplier2Product> getProductsFromSupplier2() {
        String url = "https://api.escuelajs.co/api/v1/products";
        Supplier2Product[] response =  restTemplate.exchange(url, HttpMethod.GET, null, Supplier2Product[].class).getBody();
        return Arrays.stream(response).sorted(Comparator.comparingDouble(Supplier2Product::getPrice)).toList();
    }

    public List<Supplier3Product> getProductsFromSupplier3() {
        String url = "https://api.itbook.store/1.0/search/spring?page=1";
        Supplier3Response response =  restTemplate.exchange(url, HttpMethod.GET, null, Supplier3Response.class).getBody();
        return response.getBooks();
    }

    public List<SupplierProduct> getProductsFromSupplier() {
        List<SupplierProduct> products = new ArrayList<>();
        products.addAll(getProductsFromSupplier1());
        products.addAll(getProductsFromSupplier2());
        products.addAll(getProductsFromSupplier3());
        return products;
    }
}
