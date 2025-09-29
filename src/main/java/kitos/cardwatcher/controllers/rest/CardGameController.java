package kitos.cardwatcher.controllers.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import kitos.cardwatcher.entities.CardGame;
import kitos.cardwatcher.services.CardGameService;

@RestController
@RequestMapping("/api/card-games")
public class CardGameController {
    
    @Autowired
    private CardGameService cardGameService;
    
    @GetMapping
    public List<CardGame> getAllCardGames() {
        return cardGameService.getAllCardGames();
    }
    
    @PostMapping
    public CardGame createGame(@RequestBody CardGame cardGame) {
        return cardGameService.saveCardGame(cardGame);
    }
    
    // === GET CARD GAME BY ID ===
    @GetMapping("/{id}")
    public ResponseEntity<CardGame> getCardGameById(@PathVariable("id") Long id) {
        Optional<CardGame> cardGame = cardGameService.getCardGameById(id);
        return cardGame.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    // === GET CARD GAME BY NAME ===
    @GetMapping("/name/{name}")
    public ResponseEntity<CardGame> getCardGameByName(@PathVariable("name") String name) {
        Optional<CardGame> cardGame = cardGameService.getCardGameByName(name);
        return cardGame.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }
    
    // === GET CARD GAME BY NAME (case insensitive) ===
    @GetMapping("/name/ignore-case/{name}")
    public ResponseEntity<CardGame> getCardGameByNameIgnoreCase(@PathVariable("name") String name) {
        Optional<CardGame> cardGame = cardGameService.getCardGameByNameIgnoreCase(name);
        return cardGame.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CardGame> updateCardGame(@PathVariable("id") Long id, @RequestBody CardGame cardGameDetails) {
        try {
            CardGame updatedCardGame = cardGameService.updateCardGame(id, cardGameDetails);
            return ResponseEntity.ok(updatedCardGame);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCardGame(@PathVariable("id") Long id) {
        cardGameService.deleteCardGame(id);
        return ResponseEntity.ok().build();
    }
}