package kitos.cardwatcher.repositories;

import kitos.cardwatcher.entities.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Long> {
    Optional<UserCredentials> findByUserId(Long userId);
}
