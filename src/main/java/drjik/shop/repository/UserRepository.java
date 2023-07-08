package drjik.shop.repository;


import drjik.shop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.login = ?1 and u.password = ?2")
    User checkUser(String login, String password);

    @Query("select u from User u where u.login = ?1")
    User checkUserByLogin(String login);

    Optional<User> findByLogin(String login);
}
