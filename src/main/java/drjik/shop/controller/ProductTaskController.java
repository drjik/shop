package drjik.shop.controller;

import drjik.shop.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/products")
@AllArgsConstructor
public class ProductTaskController {
    private final ProductService productService;
    private final RecallService recallService;
    private final UserService userService;
    private final OrderService orderService;

    @GetMapping()
    public String products(Model model, @RequestParam(name = "page", required = false) Integer page, @RequestParam(name = "search", required = false) String search) {
        if (page == null) {
            page = 0;
        }
        model.addAttribute("products", productService.getProduct(page, search));
        return "product/product_page";
    }

    @PostMapping()
    public String productsActive(@RequestParam(name = "addButton", required = false) Long product, @RequestParam(name = "removeButton", required = false) Long removeButton) {
        if (userService.getCurrentUser() == null) {
            return "redirect:login";
        } else {
            if (product != null) {
                orderService.addOrderProducts(userService.getCurrentUser(), productService.getProductById(product));
            } else {
                orderService.removeOrderProducts(userService.getCurrentUser(), productService.getProductById(removeButton));
            }
        }
        return "redirect:products";
    }

    @GetMapping(path = "/7")
    public String information(Model model, @RequestParam(name = "id") Long productId) {
        model.addAttribute("product", productService.getProductById(productId));
        model.addAttribute("rating", recallService.averageRating(recallService.getRecallsByProductIdTested(productId)));
        model.addAttribute("recallsTested", recallService.getRecallsByProductIdTested(productId));
        return "product/product_information";
    }

    @PostMapping(path = "/7")
    public String informationActive(
            @RequestParam(name = "id", required = false) Long productId,
            @RequestParam(name = "score", required = false) Integer score,
            @RequestParam(name = "description", required = false) String description,
            @RequestParam(name = "addButton", required = false) Long addButton,
            @RequestParam(name = "removeButton", required = false) Long removeButton) {
        if (userService.getCurrentUser() != null) {
            if (productId != null) {
                recallService.RecallAdd(userService.getCurrentUser(), productService.getProductById(productId), score, description);
                return "redirect:7?id=" + productId;
            } else if (addButton != null) {
                orderService.addOrderProducts(userService.getCurrentUser(), productService.getProductById(addButton));
                return "redirect:7?id=" + addButton;
            } else {
                orderService.removeOrderProducts(userService.getCurrentUser(), productService.getProductById(removeButton));
                return "redirect:7?id=" + removeButton;
            }
        }
        return "redirect:/login";
    }
}
