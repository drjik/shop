package drjik.shop.repository;

import drjik.shop.entity.Order;
import drjik.shop.entity.User;
import drjik.shop.entity.enumeration.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

  @Query("select o from Order o where o.user = ?1 and o.status != ?2")
  List<Order> findAllByUserAndNonStatus(User user, Status status);
  Order findByUserAndStatus(User user, Status status);

  @Query("select o from Order o where o.user = ?1 and o.status != ?2")
  Order findByUserAndNonStatus(User user, Status status);
  @Modifying
  @Transactional
  @Query("update Order o set o.deliveryAddress = ?2, o.status = ?3, o.publicationDate = ?4 where o = ?1")
  void updateCreateOrder(Order order, String address, Status status, Date date);
}
