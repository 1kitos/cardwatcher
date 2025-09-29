package kitos.cardwatcher.controllers.rest;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import kitos.cardwatcher.entities.Card;
import kitos.cardwatcher.services.CardService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    // === CARD ENDPOINTS ===
    
    @GetMapping("/{id}")
    public ResponseEntity<Card> getCardById(@PathVariable("id") Long id) {
        return cardService.getCardById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/game/id/{cardGameId}")
    public List<Card> getCardsByCardGameId(@PathVariable("cardGameId") Long cardGameId) {
        return cardService.getCardsByCardGameId(cardGameId);
    }

    @GetMapping("/game/name/{gameName}")
    public List<Card> getCardsByCardGameName(@PathVariable("gameName") String gameName) {
        return cardService.getCardsByCardGameName(gameName);
    }

    @GetMapping("/game/name/ignore-case/{gameName}")
    public List<Card> getCardsByCardGameNameIgnoreCase(@PathVariable("gameName") String gameName) {
        return cardService.getCardsByCardGameNameIgnoreCase(gameName);
    }
    
    // Additional card endpoints
    @GetMapping
    public List<Card> getAllCards() {
        return cardService.getAllCards();
    }
    
    @PostMapping
    public Card createCard(@RequestBody Card card) {
        return cardService.saveCard(card);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Card> updateCard(@PathVariable("id") Long id, @RequestBody Card cardDetails) {
        try {
            Card updatedCard = cardService.updateCard(id, cardDetails);
            return ResponseEntity.ok(updatedCard);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable("id") Long id) {
        cardService.deleteCard(id);
        return ResponseEntity.ok().build();
    }
}