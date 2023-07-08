package drjik.shop.service;

import drjik.shop.pojo.Product;
import drjik.shop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private static final Product[] PRODUCTS = new Product[] {
        new Product("Смартфон", "iPhone 14", 359_000),
        new Product("Смартфон", "Samsung Galaxy A10", 159_000),
        new Product("Наушники", "Airpods Pro", 100_000),
        new Product("Наушники", "Airpods Pro 2", 150_000)
    };

    public drjik.shop.entity.Product getProductById(Long productId) {
        return productRepository.productById(productId);
    }
}
