package kitos.cardwatcher.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import kitos.cardwatcher.entities.CardPrice;

@Repository
public interface CardPriceRepository extends JpaRepository<CardPrice, Long> {
    
    // Find prices by card printing ID
    List<CardPrice> findByCardPrintingId(Long cardPrintingId);
    
    // Find latest price for a card printing (assuming you have a timestamp field)
    List<CardPrice> findByCardPrintingIdOrderByTimestampDesc(Long cardPrintingId);
    
    // Find prices within a date range (assuming timestamp field)
    // List<CardPrice> findByCardPrintingIdAndTimestampBetween(Long cardPrintingId, Date start, Date end);
}