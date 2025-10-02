package kitos.cardwatcher.controllers.rest;

import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import kitos.cardwatcher.dtos.requests.CreateCardRequest;
import kitos.cardwatcher.dtos.requests.UpdateCardRequest;
import kitos.cardwatcher.dtos.responses.CardResponse;
import kitos.cardwatcher.dtos.shared.CardDTO;
import kitos.cardwatcher.entities.Card;
import kitos.cardwatcher.services.CardService;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cards")
@Tag(name = "Cards: Core", description = "Basic card operations")
public class CardController {

    @Autowired
    private CardService cardService;

    // === GET ALL CARDS ===
    @GetMapping
    public List<CardDTO> getAllCards() {
        return cardService.getAllCards().stream()
                .map(CardDTO::new)
                .collect(Collectors.toList());
    }

    // === GET CARD BY ID ===
    @GetMapping("/{id}")
    public ResponseEntity<CardDTO> getCardById(@PathVariable("id") Long id) {
        return cardService.getCardById(id)
                .map(card -> ResponseEntity.ok(new CardDTO(card)))
                .orElse(ResponseEntity.notFound().build());
    }

    // === GET CARDS BY CARD GAME ID ===
    @GetMapping("/game/id/{cardGameId}")
    public List<CardDTO> getCardsByCardGameId(@PathVariable("cardGameId") Long cardGameId) {
        return cardService.getCardsByCardGameId(cardGameId).stream()
                .map(CardDTO::new)
                .collect(Collectors.toList());
    }

    // === GET CARDS BY CARD GAME NAME ===
    @GetMapping("/game/name/{gameName}")
    public List<CardDTO> getCardsByCardGameName(@PathVariable("gameName") String gameName) {
        return cardService.getCardsByCardGameName(gameName).stream()
                .map(CardDTO::new)
                .collect(Collectors.toList());
    }

    // === GET CARDS BY CARD GAME NAME (case insensitive) ===
    @GetMapping("/game/name/ignore-case/{gameName}")
    public List<CardDTO> getCardsByCardGameNameIgnoreCase(@PathVariable("gameName") String gameName) {
        return cardService.getCardsByCardGameNameIgnoreCase(gameName).stream()
                .map(CardDTO::new)
                .collect(Collectors.toList());
    }
    
    // === CREATE CARD ===
    @PostMapping
    public ResponseEntity<CardResponse> createCard(@RequestBody CreateCardRequest createCardRequest) {
        Card card = createCardRequest.toCard();
        Card savedCard = cardService.saveCard(card);
        CardResponse response = new CardResponse(savedCard);
        
        return ResponseEntity
                .created(URI.create("/api/cards/" + savedCard.getId()))
                .body(response);
    }
    
    // === UPDATE CARD ===
    @PutMapping("/{id}")
    public ResponseEntity<CardResponse> updateCard(
            @PathVariable("id") Long id, 
            @RequestBody UpdateCardRequest updateCardRequest) {
        try {
            Card card = updateCardRequest.toCard();
            Card updatedCard = cardService.updateCard(id, card);
            CardResponse response = new CardResponse(updatedCard);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // === DELETE CARD ===
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable("id") Long id) {
        cardService.deleteCard(id);
        return ResponseEntity.noContent().build();
    }
}