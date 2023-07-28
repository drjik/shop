package drjik.shop.controller;

import drjik.shop.service.OrderService;
import drjik.shop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping(path = "/personal")
public class PersonalAccountController {
    private final OrderService orderService;
    private final UserService userService;

    @GetMapping()
    public String personalAccount(Model model) {
        if (userService.getCurrentUser() == null) {
            return "redirect:/cart";
        }
        model.addAttribute("orders", orderService.ordersNonStatusCart(userService.getCurrentUser()));
        return "user/personal_account";
    }
}
