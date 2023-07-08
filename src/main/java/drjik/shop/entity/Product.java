package drjik.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="products")
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private String name;

    private Integer price;

    @OneToMany(mappedBy = "product")
    private List<Value> values;

    @OneToMany(mappedBy = "product")
    private List<Recall> recalls;
}
