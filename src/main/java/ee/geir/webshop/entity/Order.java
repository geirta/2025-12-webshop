package ee.geir.webshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double total;

    @ManyToOne
    private Person person;

    private PaymentStatus paymentStatus;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderRow> orderRows; // List<OrderRow>


    // Product
    // @OneToMany
    // List<Ingredient> ingredient;  // jahu 100g

}
