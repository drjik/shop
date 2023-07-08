package drjik.shop.service;

import drjik.shop.entity.Category;
import drjik.shop.entity.Product;
import drjik.shop.repository.CategoryRepository;
import drjik.shop.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FormService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public void createProduct(Long categoryId, String productName, Integer productPrice) {
        Product product = new Product();

        List<Category> list = categoryRepository.findAllById(categoryId);
        System.out.println(list.get(0));
        product.setCategory(list.get(0));
        product.setName(productName);
        product.setPrice(productPrice);

        productRepository.save(product);
    }

    public List<Category> getCategory() {

        return categoryRepository.findAll();
    }

    public boolean checkCategoryByName(String name) {
        return categoryRepository.checkCategoryByName(name) != null;
    }

    public void createCategory(String name) {
        Category category = new Category();

        category.setName(name);

        categoryRepository.save(category);
    }
}