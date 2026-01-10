package ee.geir.webshop.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
public class Supplier2Product extends SupplierProduct{
    public int id;
    public String title;
    public String slug;
    public int price;
    public String description;
    public Category category;
    public ArrayList<String> images;
    public Date creationAt;
    public Date updatedAt;
}

@Data
class Category{
    public int id;
    public String name;
    public String slug;
    public String image;
    public Date creationAt;
    public Date updatedAt;
}