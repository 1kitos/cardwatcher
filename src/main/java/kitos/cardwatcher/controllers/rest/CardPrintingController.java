package kitos.cardwatcher.controllers.rest;

import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import kitos.cardwatcher.dtos.requests.CreateCardPriceRequest;
import kitos.cardwatcher.dtos.requests.CreateCardPrintingRequest;
import kitos.cardwatcher.dtos.requests.UpdateCardPrintingRequest;
import kitos.cardwatcher.dtos.responses.CardPrintingResponse;
import kitos.cardwatcher.dtos.shared.CardPrintingDTO;
import kitos.cardwatcher.dtos.shared.CardPriceDTO;
import kitos.cardwatcher.entities.CardPrinting;
import kitos.cardwatcher.entities.CardPrice;
import kitos.cardwatcher.services.CardPrintingService;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/card-printings")
@Tag(name = "Cards: Prices", description = "Price tracking and history")
public class CardPrintingController {

    @Autowired
    private CardPrintingService cardPrintingService;

    // === GET ALL CARD PRINTINGS ===
    @GetMapping
    public List<CardPrintingDTO> getAllCardPrintings() {
        return cardPrintingService.getAllCardPrintings().stream()
                .map(CardPrintingDTO::new)
                .collect(Collectors.toList());
    }
    
    // === GET CARD PRINTING BY ID ===
    @GetMapping("/{id}")
    public ResponseEntity<CardPrintingDTO> getCardPrintingById(@PathVariable("id") Long id) {
        return cardPrintingService.getCardPrintingById(id)
                .map(cardPrinting -> ResponseEntity.ok(new CardPrintingDTO(cardPrinting)))
                .orElse(ResponseEntity.notFound().build());
    }

    // === GET PRINTINGS BY CARD ID ===
    @GetMapping("/card/{cardId}")
    public List<CardPrintingDTO> getPrintingsByCardId(@PathVariable("cardId") Long cardId) {
        return cardPrintingService.getPrintingsByCardId(cardId).stream()
                .map(CardPrintingDTO::new)
                .collect(Collectors.toList());
    }

    // === GET PRINTINGS BY SET CODE ===
    @GetMapping("/set/{setCode}")
    public List<CardPrintingDTO> getPrintingsBySetCode(@PathVariable("setCode") String setCode) {
        return cardPrintingService.getPrintingsBySetCode(setCode).stream()
                .map(CardPrintingDTO::new)
                .collect(Collectors.toList());
    }

    // === GET PRINTINGS BY RARITY ===
    @GetMapping("/rarity/{rarity}")
    public List<CardPrintingDTO> getPrintingsByRarity(@PathVariable("rarity") String rarity) {
        return cardPrintingService.getPrintingsByRarity(rarity).stream()
                .map(CardPrintingDTO::new)
                .collect(Collectors.toList());
    }

    // === GET PRINTING BY SERIAL NUMBER ===
    @GetMapping("/serial/{serialNumber}")
    public ResponseEntity<CardPrintingDTO> getPrintingBySerialNumber(@PathVariable("serialNumber") String serialNumber) {
        return cardPrintingService.getPrintingBySerialNumber(serialNumber)
                .map(cardPrinting -> ResponseEntity.ok(new CardPrintingDTO(cardPrinting)))
                .orElse(ResponseEntity.notFound().build());
    }
    
    // === CREATE CARD PRINTING ===
    @PostMapping
    public ResponseEntity<CardPrintingResponse> createCardPrinting(@RequestBody CreateCardPrintingRequest createCardPrintingRequest) {
        CardPrinting cardPrinting = createCardPrintingRequest.toCardPrinting();
        CardPrinting savedPrinting = cardPrintingService.saveCardPrinting(cardPrinting);
        CardPrintingResponse response = new CardPrintingResponse(savedPrinting);
        
        return ResponseEntity
                .created(URI.create("/api/card-printings/" + savedPrinting.getId()))
                .body(response);
    }
    
    // === UPDATE CARD PRINTING ===
    @PutMapping("/{id}")
    public ResponseEntity<CardPrintingResponse> updateCardPrinting(
            @PathVariable("id") Long id, 
            @RequestBody UpdateCardPrintingRequest updateCardPrintingRequest) {
        try {
            CardPrinting cardPrinting = updateCardPrintingRequest.toCardPrinting();
            CardPrinting updatedPrinting = cardPrintingService.updateCardPrinting(id, cardPrinting);
            CardPrintingResponse response = new CardPrintingResponse(updatedPrinting);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // === DELETE CARD PRINTING ===
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCardPrinting(@PathVariable("id") Long id) {
        cardPrintingService.deleteCardPrinting(id);
        return ResponseEntity.noContent().build();
    }
    
    // === GET PRICE HISTORY ===
    @GetMapping("/{id}/price-history")
    public List<CardPriceDTO> getPriceHistory(@PathVariable("id") Long printingId) {
        return cardPrintingService.getPriceHistory(printingId).stream()
                .map(CardPriceDTO::new)
                .collect(Collectors.toList());
    }
    
    // === ADD PRICE TO PRINTING ===
    @PostMapping("/{id}/prices")
    public ResponseEntity<CardPrintingResponse> addPriceToPrinting(
        @PathVariable("id") Long printingId, 
        @RequestBody CreateCardPriceRequest createCardPriceRequest) {
        try {
            CardPrice cardPrice = createCardPriceRequest.toCardPrice();
            CardPrinting updatedPrinting = cardPrintingService.addPriceToPrinting(printingId, cardPrice);
            CardPrintingResponse response = new CardPrintingResponse(updatedPrinting);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}