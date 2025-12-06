package ee.geir.webshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private double price;
    private int stock;
    private boolean active;

    // Parempoolne t2hendab, et siin failis peab olema List
    // @ManyToMany
    // @OneToMany
    // @ManyToOne
    // @OneToOne

    @ManyToOne
    private Category category;

    // @OneToOne
    // private EanCode {issuer: "", code: ""}

    // Person
    // @OneToOne
    // private Address

    // @ManyToOne
    // private Sugu

}
