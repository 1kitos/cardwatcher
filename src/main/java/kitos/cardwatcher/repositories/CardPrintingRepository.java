package kitos.cardwatcher.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import kitos.cardwatcher.entities.CardPrinting;
import java.util.List;
import java.util.Optional;

@Repository
public interface CardPrintingRepository extends JpaRepository<CardPrinting, Long> {
    
    // Find by card
    List<CardPrinting> findByCardId(Long cardId);
    List<CardPrinting> findByCardIdOrderBySetCode(Long cardId);
    
    // Find by set code
    List<CardPrinting> findBySetCode(String setCode);
    List<CardPrinting> findBySetCodeOrderBySerialNumber(String setCode);
    
    // Find by rarity
    List<CardPrinting> findByRarity(String rarity);
    
    // Find by serial number
    Optional<CardPrinting> findBySerialNumber(String serialNumber);
    
    // Combined queries
    List<CardPrinting> findBySetCodeAndRarity(String setCode, String rarity);
    List<CardPrinting> findByCardIdAndSetCode(Long cardId, String setCode);
}