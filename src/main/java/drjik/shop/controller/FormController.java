package drjik.shop.controller;

import drjik.shop.service.FormService;
import drjik.shop.service.RecallService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping(path = "/form_controller")
public class FormController {
    private final FormService formService;
    private final RecallService recallService;

    @GetMapping(path = "/add_product")
    public String formPage(Model model) {
        model.addAttribute(formService.getCategory());
        return "form_page";
    }

    @PostMapping("/add_product")
    public String formAction(
            @RequestParam(name = "productCategoryId") Long productCategoryId,
            @RequestParam(name = "productName") String productName,
            @RequestParam(name = "productPrice") Integer productPrice){
        formService.createProduct(productCategoryId, productName, productPrice);
        return "redirect:/form_controller/add_product";
    }

    @GetMapping(path = "/add_category")
    public String categoryPage(Model model) {
        model.addAttribute(formService.getCategory());
        return "category_form_page";
    }

    @PostMapping("/add_category")
    public String categoryAction(@RequestParam(name = "categoryName") String categoryName){
        if (formService.checkCategoryByName(categoryName)) {
            return "redirect:/form_controller/add_category";
        } else {
            formService.createCategory(categoryName);
            return "redirect:/form_controller/add_product";
        }
    }

    @GetMapping(path="/check_recalls")
    public String recallsPage(Model model) {
        model.addAttribute("recalls", recallService.getRecallsNonTested());
        return "recalls_form_page";
    }

    @PostMapping(path = "/check_recalls")
    public String recallsAction(@RequestParam(name = "button") String button) {
        String[] parts = button.split("-");

        if (parts[1].equals("confirm")) {
            recallService.confirmRecall(Long.parseLong(parts[0]));
        } else if (parts[1].equals("delete")) {
            recallService.deleteRecall(Long.parseLong(parts[0]));
        }

        return "redirect:/from_controller/check_recalls";
    }
}
