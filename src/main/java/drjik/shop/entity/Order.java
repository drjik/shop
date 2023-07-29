package drjik.shop.entity;

import drjik.shop.entity.enumeration.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Enumerated
  private Status status;

  private String deliveryAddress;

  private LocalDate publicationDate;

  @OneToMany(mappedBy = "order")
  private List<OrderProducts> products;
}
