package drjik.shop.controller;

import drjik.shop.service.OrderService;
import drjik.shop.service.ProductService;
import drjik.shop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/cart")
@AllArgsConstructor
public class CartController {
  private final OrderService orderService;
  private final UserService userService;
  private final ProductService productService;

  @GetMapping()
  public String cart(Model model) {
    model.addAttribute("products", orderService.productsListWithUniqueValues(userService.getCurrentUser()));
    model.addAttribute("totalPrice", orderService.totalPrice(userService.getCurrentUser()));
    return "cart/first_cart_page";
  }

  @PostMapping()
  public String cartActive(
          @RequestParam(name = "removeButton", required = false) Long removeButton,
          @RequestParam(name = "addButton", required = false) Long addButton
  ) {
    if (removeButton != null) {
      orderService.removeOrderProducts(userService.getCurrentUser(), productService.getProductById(removeButton));
    } else if (addButton != null) {
      orderService.addOrderProducts(userService.getCurrentUser(), productService.getProductById(addButton));
    } else {
      return "redirect:/cart/checkout";
    }
    return "redirect:/cart";
  }

  @GetMapping(path = "/checkout")
  public String checkout(Model model) {
    model.addAttribute("products", orderService.productsListWithUniqueValues(userService.getCurrentUser()));
    model.addAttribute("totalPrice", orderService.totalPrice(userService.getCurrentUser()));
    return "cart/second_cart_page";
  }

  @PostMapping(path = "/checkout")
  public String checkoutActive(@RequestParam(name = "address") String address) {
    if (address != null) {
      orderService.updateCreateOrder(userService.getCurrentUser(), address);
      return "redirect:/cart";
    }
    return "redirect:/cart/checkout";
  }
}
