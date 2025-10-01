package kitos.cardwatcher.controllers.rest;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import kitos.cardwatcher.entities.CardPrice;
import kitos.cardwatcher.entities.CardPrinting;
import kitos.cardwatcher.services.CardPrintingService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/card-printings")
public class CardPrintingController {

    @Autowired
    private CardPrintingService cardPrintingService;

    // === CARD PRINTING ENDPOINTS ===
    
    @GetMapping("/{id}")
    public ResponseEntity<CardPrinting> getCardPrintingById(@PathVariable("id") Long id) {
        return cardPrintingService.getCardPrintingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/card/{cardId}")
    public List<CardPrinting> getPrintingsByCardId(@PathVariable("cardId") Long cardId) {
        return cardPrintingService.getPrintingsByCardId(cardId);
    }

    @GetMapping("/set/{setCode}")
    public List<CardPrinting> getPrintingsBySetCode(@PathVariable("setCode") String setCode) {
        return cardPrintingService.getPrintingsBySetCode(setCode);
    }

    @GetMapping("/rarity/{rarity}")
    public List<CardPrinting> getPrintingsByRarity(@PathVariable("rarity") String rarity) {
        return cardPrintingService.getPrintingsByRarity(rarity);
    }

    @GetMapping("/serial/{serialNumber}")
    public ResponseEntity<CardPrinting> getPrintingBySerialNumber(@PathVariable("serialNumber") String serialNumber) {
        return cardPrintingService.getPrintingBySerialNumber(serialNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // Additional card printing endpoints
    @GetMapping
    public List<CardPrinting> getAllCardPrintings() {
        return cardPrintingService.getAllCardPrintings();
    }
    
    @PostMapping
    public CardPrinting createCardPrinting(@RequestBody CardPrinting cardPrinting) {
        return cardPrintingService.saveCardPrinting(cardPrinting);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CardPrinting> updateCardPrinting(@PathVariable("id") Long id, @RequestBody CardPrinting cardPrintingDetails) {
        try {
            CardPrinting updatedPrinting = cardPrintingService.updateCardPrinting(id, cardPrintingDetails);
            return ResponseEntity.ok(updatedPrinting);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCardPrinting(@PathVariable("id") Long id) {
        cardPrintingService.deleteCardPrinting(id);
        return ResponseEntity.ok().build();
    }
    
    
    @GetMapping("/{id}/price-history")
    public List<CardPrice> getPriceHistory(@PathVariable("id") Long printingId) {
        return cardPrintingService.getPriceHistory(printingId);
    }
    
    @PostMapping("/{id}/prices")
    public CardPrinting addPriceToPrinting(
        @PathVariable("id") Long printingId, 
        @RequestBody CardPrice cardPrice) {
        return cardPrintingService.addPriceToPrinting(printingId, cardPrice);
    }
    
    
}