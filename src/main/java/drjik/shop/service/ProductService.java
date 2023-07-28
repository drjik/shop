package drjik.shop.service;

import drjik.shop.entity.Product;
import drjik.shop.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public drjik.shop.entity.Product getProductById(Long productId) {
        return productRepository.productById(productId);
    }
    public List<Product> getProduct(Integer numberPage, String search) {
        Pageable pageable = PageRequest.of(numberPage, 2);
        Page<Product> productPage = productRepository.findAll(pageable);

        if (search != null) {
            productPage = productRepository.findAllByName(search, pageable);
        }

        return productPage.getContent();
    }


    public List<Product> getList() {
        return productRepository.findAll();
    }
}
