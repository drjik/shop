package drjik.shop.service;

import drjik.shop.entity.Order;
import drjik.shop.entity.OrderProducts;
import drjik.shop.entity.Product;
import drjik.shop.entity.User;
import drjik.shop.entity.enumeration.Status;
import drjik.shop.repository.OrderProductsRepository;
import drjik.shop.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service

public class OrderService {
  private final OrderRepository orderRepository;
  private final OrderProductsRepository orderProductsRepository;

  public OrderService(OrderRepository orderRepository, OrderProductsRepository orderProductsRepository) {
    this.orderRepository = orderRepository;
    this.orderProductsRepository = orderProductsRepository;
  }

  public List<Order> ordersNonStatusCart(User user) {
    return orderRepository.findAllByUserAndNonStatus(user, Status.CART);
  }

  public boolean isProductsInCart(User user) {
    if (user == null) return false;
    Order order = orderRepository.findByUserAndStatus(user, Status.CART);
    if (order == null) return false;

    try {
      return order.getProducts().get(0) != null;
    } catch (Exception ignored) {}
    return false;
  }

  public boolean isProductInCart(User user, Product product) {
    if (user == null) return false;
    Order order = orderRepository.findByUserAndStatus(user, Status.CART);
    if (order == null) return false;
    for (OrderProducts orderProduct : order.getProducts()) {
      if (orderProduct.getProduct().equals(product)) {
        return true;
      }
    }
    return false;
  }

  public int countProductsInOrder(User user, Product product) {
    int count = 0;
    Order order = orderRepository.findByUserAndStatus(user, Status.CART);
    for (OrderProducts orderProduct : order.getProducts()) {
      if (orderProduct.getProduct().equals(product)) {
        count += 1;
      }
    }
    return count;
  }

  public int countProductsInOrder(Order order, Product product) {
    int count = 0;
    for (OrderProducts orderProduct : order.getProducts()) {
      if (orderProduct.getProduct().equals(product)) {
        count += 1;
      }
    }
    return count;
  }

  public List<Product> productsListWithUniqueValues(User user) {
    return orderProductsRepository.findAllProductsByOrderWithUniqueValues(orderRepository.findByUserAndStatus(user, Status.CART));
  }

  public List<Product> productsListWithUniqueValues(Order order) {
    return orderProductsRepository.findAllProductsByOrderWithUniqueValues(order);
  }

  private List<Product> productsList(Order order) {
    List<Product> products = new ArrayList<>();

    for (OrderProducts orderProducts : orderProductsRepository.findAllOrderProductsByOrder(order)) {
      products.add(orderProducts.getProduct());
    }

    return products;
  }

  public int totalPrice(User user) {
    int count = 0;
    for (Product product : productsList(orderRepository.findByUserAndStatus(user, Status.CART))) {
       count += product.getPrice();
    }
    return count;
  }
  public int totalPrice(Order order) {
    int count = 0;
    for (Product product : productsList(order)) {
       count += product.getPrice();
    }
    return count;
  }

  public void addOrder(User user, Status status, String deliveryAddress, Date publicationDate) {
    Order order = new Order();

    order.setUser(user);
    order.setStatus(status);
    order.setDeliveryAddress(deliveryAddress);
    order.setPublicationDate(publicationDate);

    orderRepository.save(order);
  }

  public void addOrderProducts(User user, Product product) {
    if (orderRepository.findByUserAndStatus(user, Status.CART) != null) {
      OrderProducts orderProducts = new OrderProducts();

      orderProducts.setOrder(orderRepository.findByUserAndStatus(user, Status.CART));
      orderProducts.setProduct(product);
      orderProductsRepository.save(orderProducts);
    } else {
      addOrder(user, Status.CART, null, Calendar.getInstance().getTime());
      addOrderProducts(user, product);
    }
  }

  public void updateCreateOrder(User user, String address) {
    Order order = orderRepository.findByUserAndStatus(user, Status.CART);
    orderRepository.updateCreateOrder(order, address, Status.ORDER_CREATED, Calendar.getInstance().getTime());
  }

  public void removeOrderProducts(User user, Product product) {
    List<OrderProducts> orderProducts = orderProductsRepository.findAllByOrderAndProduct(orderRepository.findByUserAndStatus(user, Status.CART), product);
    orderProductsRepository.deleteOrderProducts(orderProducts.get(0));
  }
}