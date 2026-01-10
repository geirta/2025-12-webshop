package ee.geir.webshop.model;

import lombok.Data;

@Data
public class Supplier3Product extends SupplierProduct {
    private String title;
    private String subtitle;
    private String isbn13;
    private String price;
    private String image;
    private String url;
}
