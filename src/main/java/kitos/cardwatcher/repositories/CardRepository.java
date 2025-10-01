package kitos.cardwatcher.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import kitos.cardwatcher.entities.Card;
import kitos.cardwatcher.entities.CardGame;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    
    // Custom query methods
    List<Card> findByCardGameId(Long cardGameId);
    
    List<Card> findByNameContainingIgnoreCase(String name);
    
    Card findByNameAndCardGame(String name, CardGame cardGame);
    
    
}
