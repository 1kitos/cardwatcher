// kitos.cardwatcher.repositories.WatchlistRepository.java
package kitos.cardwatcher.repositories;

import kitos.cardwatcher.entities.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface WatchlistRepository extends JpaRepository<Watchlist, Long> 
{	
    List<Watchlist> findByUserId(Long userId);
    List<Watchlist> findByUserUsername(String username);
    Optional<Watchlist> findByIdAndUserId(Long id, Long userId);
    boolean existsByNameAndUserId(String name, Long userId);
}