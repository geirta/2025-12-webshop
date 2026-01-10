package ee.geir.webshop.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Supplier3Response {
    private String error;
    private String total;
    private String page;
    private ArrayList<Supplier3Product> books;
}
