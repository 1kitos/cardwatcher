package kitos.cardwatcher.controllers.rest;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import kitos.cardwatcher.entities.CardPrice;
import kitos.cardwatcher.services.CardPriceService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/prices")
public class CardPriceController {

    @Autowired
    private CardPriceService cardPriceService;

    // === PRICE ENDPOINTS ===
    
    @GetMapping("/{id}")
    public ResponseEntity<CardPrice> getCardPriceById(@PathVariable("id") Long id) {
        return cardPriceService.getCardPriceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/printing/{cardPrintingId}")
    public List<CardPrice> getPricesByCardPrintingId(@PathVariable("cardPrintingId") Long cardPrintingId) {
        return cardPriceService.getPricesByCardPrintingId(cardPrintingId);
    }

    @GetMapping("/printing/{cardPrintingId}/latest")
    public List<CardPrice> getLatestPricesByCardPrintingId(@PathVariable("cardPrintingId") Long cardPrintingId) {
        return cardPriceService.getLatestPricesByCardPrintingId(cardPrintingId);
    }

    // Additional price endpoints
    @GetMapping
    public List<CardPrice> getAllCardPrices() {
        return cardPriceService.getAllCardPrices();
    }
    
    @PostMapping
    public CardPrice createCardPrice(@RequestBody CardPrice cardPrice) {
        return cardPriceService.saveCardPrice(cardPrice);
    }

    @PostMapping("/printing/{cardPrintingId}")
    public ResponseEntity<CardPrice> createPriceForPrinting(
            @PathVariable("cardPrintingId") Long cardPrintingId, 
            @RequestBody CardPrice cardPrice) {
        try {
            CardPrice createdPrice = cardPriceService.createPriceForPrinting(cardPrintingId, cardPrice);
            return ResponseEntity.ok(createdPrice);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CardPrice> updateCardPrice(@PathVariable("id") Long id, @RequestBody CardPrice priceDetails) {
        try {
            CardPrice updatedPrice = cardPriceService.updateCardPrice(id, priceDetails);
            return ResponseEntity.ok(updatedPrice);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCardPrice(@PathVariable("id") Long id) {
        cardPriceService.deleteCardPrice(id);
        return ResponseEntity.ok().build();
    }
}