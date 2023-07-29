package drjik.shop.service;

import drjik.shop.entity.Product;
import drjik.shop.entity.Recall;
import drjik.shop.entity.User;
import drjik.shop.repository.RecallRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RecallService {
    private final RecallRepository recallRepository;

    public RecallService(RecallRepository recallRepository) {
        this.recallRepository = recallRepository;
    }

    public void RecallAdd(User user, Product product, int score, String description) {
        Recall recall = new Recall();

        recall.setUser(user);
        recall.setProduct(product);
        recall.setScore(score);
        recall.setDescription(description);
        recall.setPublication_date(LocalDate.now());

        recallRepository.save(recall);
    }

    public List<Recall> getRecallsByProductIdTested(Long productId) {
        return recallRepository.findAllByProductIdTested(productId);
    }

    public List<Recall> getRecallsNonTested() {
        return recallRepository.findAllNonTested();
    }

    public void confirmRecall(Long id) {
        recallRepository.setRecallByIdTested(id);
    }

    public void deleteRecall(Long id) {
        recallRepository.deleteRecallById(id);
    }

    public int averageRating(List<Recall> recalls) {
        if (recalls.size() == 0) {
            return 0;
        }

        int count = 0;

        for (Recall recall : recalls) {
            count += recall.getScore();
        }

        return count/recalls.size();
    }

    public boolean isRecallInRecalls(User user, Product product) {
        return recallRepository.findByUser(user, product) != null;
    }
}
