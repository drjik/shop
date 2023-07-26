package drjik.shop.repository;

import drjik.shop.entity.Order;
import drjik.shop.entity.OrderProducts;
import drjik.shop.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderProductsRepository extends JpaRepository<OrderProducts, Long> {

    @Query("select o from OrderProducts o where o.order = ?1")
    List<OrderProducts> findAllOrderProductsByOrder(Order order);
    @Query("select distinct o.product from OrderProducts o where o.order = ?1")
    List<Product> findAllProductsByOrderWithUniqueValues(Order order);

    List<OrderProducts> findAllByOrderAndProduct(Order order, Product product);

    @Modifying
    @Transactional
    @Query("delete from OrderProducts o where o = ?1")
    void deleteOrderProducts(OrderProducts orderProducts);
    @Modifying
    @Transactional
    @Query("delete from OrderProducts o where o.order = ?1")
    void deleteOrderProductsByOrder(Order order);
}
