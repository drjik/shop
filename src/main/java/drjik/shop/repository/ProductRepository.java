package drjik.shop.repository;

import drjik.shop.entity.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategoryName(String categoryName);

    @Query("select p from Product p where p.id = ?1")
    Product productById(Long id);

}
