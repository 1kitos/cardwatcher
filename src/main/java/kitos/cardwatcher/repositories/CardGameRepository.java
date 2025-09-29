package kitos.cardwatcher.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import kitos.cardwatcher.entities.CardGame;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardGameRepository extends JpaRepository<CardGame, Long> {
    
    // Find by exact name
    Optional<CardGame> findByName(String name);
    
    // Find by name ignoring case
    Optional<CardGame> findByNameIgnoreCase(String name);
    
    // Check if a game exists by name
    boolean existsByName(String name);
    
    // Check if a game exists by name (case insensitive)
    boolean existsByNameIgnoreCase(String name);
}