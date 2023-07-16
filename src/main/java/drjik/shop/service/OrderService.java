package drjik.shop.service;

import drjik.shop.entity.Product;
import drjik.shop.entity.User;
import drjik.shop.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service

public class OrderService {
  private final OrderRepository orderRepository;

  public OrderService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  public void addOrder(User user, Product product) {
    System.out.println(orderRepository.findByUser(user));
  }
}
