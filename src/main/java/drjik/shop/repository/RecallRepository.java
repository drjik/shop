package drjik.shop.repository;

import drjik.shop.entity.Recall;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecallRepository extends JpaRepository<Recall, Long> {
    @Query("select r from Recall r where r.product.id = ?1 and r.tested = true")
    List<Recall> findAllByProductIdTested(Long id);

    @Query("select r from Recall r where r.id = ?1 and r.tested = false")
    List<Recall> findAllByProductIdNonTested(Long id);
    @Query("select r from Recall r where r.tested = false")
    List<Recall> findAllNonTested();

    @Modifying
    @Transactional
    @Query("update Recall r set r.tested = true where r.id = ?1")
    void setRecallByIdTested(Long id);

    @Modifying
    @Transactional
    @Query("delete from Recall r where r.id = ?1")
    void deleteRecallById(Long id);
}
