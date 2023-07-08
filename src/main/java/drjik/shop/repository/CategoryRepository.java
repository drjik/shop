package drjik.shop.repository;

import drjik.shop.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllById(Long Id);

    @Query("select c from Category c where c.name = ?1")
    Category checkCategoryByName(String name);
}
