package drjik.shop.entity;

import drjik.shop.entity.enumeration.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated
    private Role role;

    private String login;
    private String password;
    private String name;
    private String lastname;
    private Date data_registration;

    @OneToMany(mappedBy = "user")
    private List<Recall> recalls;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;
}
