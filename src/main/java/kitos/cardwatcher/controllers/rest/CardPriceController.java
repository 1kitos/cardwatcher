package kitos.cardwatcher.controllers.rest;

import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    @Operation(summary = "Get all card prices")
    public List<CardPriceDTO> getAllCardPrices() {
        return cardPriceService.getAllCardPrices().stream()
                .map(CardPriceDTO::new)
                .collect(Collectors.toList());
    }

    // === GET CARD PRICE BY ID ===
    @GetMapping("/{id}")
    @Operation(summary = "Get card price by ID")
    public ResponseEntity<CardPriceDTO> getCardPriceById(
            @PathVariable("id") @Parameter(description = "Card price ID", example = "1") Long id) {
        return cardPriceService.getCardPriceById(id)
                .map(cardPrice -> ResponseEntity.ok(new CardPriceDTO(cardPrice)))
                .orElse(ResponseEntity.notFound().build());
    }

    // === GET PRICES BY CARD PRINTING ID ===
    @GetMapping("/printing/{cardPrintingId}")
    @Operation(summary = "Get prices by card printing ID")
    public List<CardPriceDTO> getPricesByCardPrintingId(
            @PathVariable("cardPrintingId") @Parameter(description = "Card printing ID", example = "123") Long cardPrintingId) {
        return cardPriceService.getPricesByCardPrintingId(cardPrintingId).stream()
                .map(CardPriceDTO::new)
                .collect(Collectors.toList());
    }

    // === GET LATEST PRICES BY CARD PRINTING ID ===
    @GetMapping("/printing/{cardPrintingId}/latest")
    @Operation(summary = "Get latest prices by card printing ID")
    public List<CardPriceDTO> getLatestPricesByCardPrintingId(
            @PathVariable("cardPrintingId") @Parameter(description = "Card printing ID", example = "123") Long cardPrintingId) {
        return cardPriceService.getLatestPricesByCardPrintingId(cardPrintingId).stream()
                .map(CardPriceDTO::new)
                .collect(Collectors.toList());
    }
    
    // === CREATE CARD PRICE ===
    @PostMapping
    @Operation(summary = "Create a new card price")
    public ResponseEntity<CardPriceResponse> createCardPrice(
            @RequestBody @Parameter(description = "Card price creation data") CreateCardPriceRequest createCardPriceRequest) {
        CardPrice cardPrice = createCardPriceRequest.toCardPrice();
        CardPrice savedCardPrice = cardPriceService.saveCardPrice(cardPrice);
        CardPriceResponse response = new CardPriceResponse(savedCardPrice);
        
        return ResponseEntity
                .created(URI.create("/api/prices/" + savedCardPrice.getId()))
                .body(response);
    }

    // === CREATE PRICE FOR SPECIFIC PRINTING ===
    @PostMapping("/printing/{cardPrintingId}")
    @Operation(summary = "Create price for specific printing")
    public ResponseEntity<CardPriceResponse> createPriceForPrinting(
            @PathVariable("cardPrintingId") @Parameter(description = "Card printing ID", example = "123") Long cardPrintingId, 
            @RequestBody @Parameter(description = "Card price data") CreateCardPriceRequest createCardPriceRequest) {
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
    @Operation(summary = "Update a card price")
    public ResponseEntity<CardPriceResponse> updateCardPrice(
            @PathVariable("id") @Parameter(description = "Card price ID", example = "1") Long id, 
            @RequestBody @Parameter(description = "Card price update data") UpdateCardPriceRequest updateCardPriceRequest) {
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
    @Operation(summary = "Delete a card price")
    public ResponseEntity<Void> deleteCardPrice(
            @PathVariable("id") @Parameter(description = "Card price ID", example = "1") Long id) {
        cardPriceService.deleteCardPrice(id);
        return ResponseEntity.noContent().build();
    }
}