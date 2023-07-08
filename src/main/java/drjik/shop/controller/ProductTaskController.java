package drjik.shop.controller;

import drjik.shop.service.DataService;
import drjik.shop.service.ProductService;
import drjik.shop.service.RecallService;
import drjik.shop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
@RequestMapping(path = "/products")
@AllArgsConstructor
public class ProductTaskController {
    private final DataService dataService;
    private final ProductService productService;
    private final RecallService recallService;
    private final UserService userService;

    @GetMapping()
    public String secondResource(Model model, @RequestParam(name = "page", required = false) Integer page) {
        model.addAttribute("products", dataService.getProduct(Objects.requireNonNullElse(page, 0)));
        return "data/second_resource_page";
    }

    @GetMapping(path = "/7")
    public String information(Model model, @RequestParam(name = "id") Long productId) {
        model.addAttribute("product", productService.getProductById(productId));
        model.addAttribute("recallsTested", recallService.getRecallsByProductIdTested(productId));
        return "product/product_information";
    }

    @PostMapping(path = "/7")
    public String informationActive(@RequestParam(name = "id") Long productId, @RequestParam(name = "score") Integer score, @RequestParam(name = "description") String description) {
        if (userService.getCurrentUser() != null) {
            recallService.RecallAdd(userService.getCurrentUser(), productService.getProductById(productId), score, description);
            return "redirect:/products/7?id=" + productId;
        }
        return "redirect:/login";
    }
}
