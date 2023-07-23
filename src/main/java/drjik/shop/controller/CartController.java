package drjik.shop.controller;

import drjik.shop.service.OrderService;
import drjik.shop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/cart")
@AllArgsConstructor
public class CartController {
  private final OrderService orderService;
  private final UserService userService;

  @GetMapping()
  public String cart(Model model) {
//    System.out.println(orderService.productsList(userService.getCurrentUser()));
    return "cart/first_cart_page";
  }
}
