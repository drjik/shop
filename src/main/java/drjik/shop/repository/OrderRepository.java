package drjik.shop.repository;

import drjik.shop.entity.Order;
import drjik.shop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {
  @Query("select o from Order o where o.user = ?1")
  Order findByUser(User user);
}
