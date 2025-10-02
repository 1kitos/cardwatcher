package kitos.cardwatcher.controllers.rest;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import kitos.cardwatcher.dtos.requests.CreateCardGameRequest;
import kitos.cardwatcher.dtos.requests.UpdateCardGameRequest;
import kitos.cardwatcher.dtos.responses.CardGameResponse;
import kitos.cardwatcher.dtos.shared.CardGameDTO;
import kitos.cardwatcher.entities.CardGame;
import kitos.cardwatcher.services.CardGameService;

@RestController
@RequestMapping("/api/card-games")
@Tag(name = "Cards: Games", description = "Card game management")
public class CardGameController {
    
    @Autowired
    private CardGameService cardGameService;
    
    @GetMapping
    public List<CardGameDTO> getAllCardGames() {
        return cardGameService.getAllCardGames().stream()
            .map(CardGameDTO::new)
            .collect(Collectors.toList());
    }
    
    @PostMapping
    public ResponseEntity<CardGameResponse> createGame(@RequestBody CreateCardGameRequest cardGameRequest) {
        CardGame cardGame = cardGameRequest.toCardGame();
        CardGame savedGame = cardGameService.saveCardGame(cardGame);
        CardGameResponse response = new CardGameResponse(savedGame);
        
        return ResponseEntity
            .created(URI.create("/api/card-games/" + savedGame.getId()))
            .body(response);
    }
    
    // === GET CARD GAME BY ID ===
    @GetMapping("/{id}")
    public ResponseEntity<CardGameDTO> getCardGameById(@PathVariable("id") Long id) {
        return cardGameService.getCardGameById(id)
            .map(cardGame -> ResponseEntity.ok(new CardGameDTO(cardGame)))
            .orElse(ResponseEntity.notFound().build());
    }

    // === GET CARD GAME BY NAME ===
    @GetMapping("/name/{name}")
    public ResponseEntity<CardGameDTO> getCardGameByName(@PathVariable("name") String name) {
        return cardGameService.getCardGameByName(name)
            .map(cardGame -> ResponseEntity.ok(new CardGameDTO(cardGame)))
            .orElse(ResponseEntity.notFound().build());
    }
    
    // === GET CARD GAME BY NAME (case insensitive) ===
    @GetMapping("/name/ignore-case/{name}")
    public ResponseEntity<CardGameDTO> getCardGameByNameIgnoreCase(@PathVariable("name") String name) {
        return cardGameService.getCardGameByNameIgnoreCase(name)
            .map(cardGame -> ResponseEntity.ok(new CardGameDTO(cardGame)))
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CardGameResponse> updateCardGame(
            @PathVariable("id") Long id, 
            @RequestBody UpdateCardGameRequest updateRequest) {
        try {
            CardGame cardGame = updateRequest.toCardGame();
            CardGame updatedCardGame = cardGameService.updateCardGame(id, cardGame);
            CardGameResponse response = new CardGameResponse(updatedCardGame);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCardGame(@PathVariable("id") Long id) {
        cardGameService.deleteCardGame(id);
        return ResponseEntity.noContent().build(); // 204 No Content is more RESTful for DELETE
    }
}