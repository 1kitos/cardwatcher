package kitos.cardwatcher.controllers.rest;

import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import kitos.cardwatcher.dtos.requests.CreateCardPriceRequest;
import kitos.cardwatcher.dtos.requests.UpdateCardPriceRequest;
import kitos.cardwatcher.dtos.responses.CardPriceResponse;
import kitos.cardwatcher.dtos.shared.CardPriceDTO;
import kitos.cardwatcher.entities.CardPrice;
import kitos.cardwatcher.services.CardPriceService;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/prices")
@Tag(name = "Cards: Printings", description = "Card printing operations")
public class CardPriceController {

    @Autowired
    private CardPriceService cardPriceService;

    // === GET ALL CARD PRICES ===
    @GetMapping
    public List<CardPriceDTO> getAllCardPrices() {
        return cardPriceService.getAllCardPrices().stream()
                .map(CardPriceDTO::new)
                .collect(Collectors.toList());
    }

    // === GET CARD PRICE BY ID ===
    @GetMapping("/{id}")
    public ResponseEntity<CardPriceDTO> getCardPriceById(@PathVariable("id") Long id) {
        return cardPriceService.getCardPriceById(id)
                .map(cardPrice -> ResponseEntity.ok(new CardPriceDTO(cardPrice)))
                .orElse(ResponseEntity.notFound().build());
    }

    // === GET PRICES BY CARD PRINTING ID ===
    @GetMapping("/printing/{cardPrintingId}")
    public List<CardPriceDTO> getPricesByCardPrintingId(@PathVariable("cardPrintingId") Long cardPrintingId) {
        return cardPriceService.getPricesByCardPrintingId(cardPrintingId).stream()
                .map(CardPriceDTO::new)
                .collect(Collectors.toList());
    }

    // === GET LATEST PRICES BY CARD PRINTING ID ===
    @GetMapping("/printing/{cardPrintingId}/latest")
    public List<CardPriceDTO> getLatestPricesByCardPrintingId(@PathVariable("cardPrintingId") Long cardPrintingId) {
        return cardPriceService.getLatestPricesByCardPrintingId(cardPrintingId).stream()
                .map(CardPriceDTO::new)
                .collect(Collectors.toList());
    }
    
    // === CREATE CARD PRICE ===
    @PostMapping
    public ResponseEntity<CardPriceResponse> createCardPrice(@RequestBody CreateCardPriceRequest createCardPriceRequest) {
        CardPrice cardPrice = createCardPriceRequest.toCardPrice();
        CardPrice savedCardPrice = cardPriceService.saveCardPrice(cardPrice);
        CardPriceResponse response = new CardPriceResponse(savedCardPrice);
        
        return ResponseEntity
                .created(URI.create("/api/prices/" + savedCardPrice.getId()))
                .body(response);
    }

    // === CREATE PRICE FOR SPECIFIC PRINTING ===
    @PostMapping("/printing/{cardPrintingId}")
    public ResponseEntity<CardPriceResponse> createPriceForPrinting(
            @PathVariable("cardPrintingId") Long cardPrintingId, 
            @RequestBody CreateCardPriceRequest createCardPriceRequest) {
        try {
            CardPrice cardPrice = createCardPriceRequest.toCardPrice();
            CardPrice createdPrice = cardPriceService.createPriceForPrinting(cardPrintingId, cardPrice);
            CardPriceResponse response = new CardPriceResponse(createdPrice);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // === UPDATE CARD PRICE ===
    @PutMapping("/{id}")
    public ResponseEntity<CardPriceResponse> updateCardPrice(
            @PathVariable("id") Long id, 
            @RequestBody UpdateCardPriceRequest updateCardPriceRequest) {
        try {
            CardPrice cardPrice = updateCardPriceRequest.toCardPrice();
            CardPrice updatedPrice = cardPriceService.updateCardPrice(id, cardPrice);
            CardPriceResponse response = new CardPriceResponse(updatedPrice);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // === DELETE CARD PRICE ===
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCardPrice(@PathVariable("id") Long id) {
        cardPriceService.deleteCardPrice(id);
        return ResponseEntity.noContent().build();
    }
}