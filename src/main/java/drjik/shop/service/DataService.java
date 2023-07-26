package drjik.shop.service;

import drjik.shop.entity.Product;
import drjik.shop.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DataService {
    private final ProductRepository productRepository;
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
