package kitos.cardwatcher.controllers.rest;

import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    @Operation(summary = "Get all card printings")
    public List<CardPrintingDTO> getAllCardPrintings() {
        return cardPrintingService.getAllCardPrintings().stream()
                .map(CardPrintingDTO::new)
                .collect(Collectors.toList());
    }
    
    // === GET CARD PRINTING BY ID ===
    @GetMapping("/{id}")
    @Operation(summary = "Get card printing by ID")
    public ResponseEntity<CardPrintingDTO> getCardPrintingById(
            @PathVariable("id") @Parameter(description = "Card printing ID", example = "1") Long id) {
        return cardPrintingService.getCardPrintingById(id)
                .map(cardPrinting -> ResponseEntity.ok(new CardPrintingDTO(cardPrinting)))
                .orElse(ResponseEntity.notFound().build());
    }

    // === GET PRINTINGS BY CARD ID ===
    @GetMapping("/card/{cardId}")
    @Operation(summary = "Get printings by card ID")
    public List<CardPrintingDTO> getPrintingsByCardId(
            @PathVariable("cardId") @Parameter(description = "Card ID", example = "123") Long cardId) {
        return cardPrintingService.getPrintingsByCardId(cardId).stream()
                .map(CardPrintingDTO::new)
                .collect(Collectors.toList());
    }

    // === GET PRINTINGS BY SET CODE ===
    @GetMapping("/set/{setCode}")
    @Operation(summary = "Get printings by set code")
    public List<CardPrintingDTO> getPrintingsBySetCode(
            @PathVariable("setCode") @Parameter(description = "Set code", example = "LGN") String setCode) {
        return cardPrintingService.getPrintingsBySetCode(setCode).stream()
                .map(CardPrintingDTO::new)
                .collect(Collectors.toList());
    }

    // === GET PRINTINGS BY RARITY ===
    @GetMapping("/rarity/{rarity}")
    @Operation(summary = "Get printings by rarity")
    public List<CardPrintingDTO> getPrintingsByRarity(
            @PathVariable("rarity") @Parameter(description = "Rarity", example = "Rare") String rarity) {
        return cardPrintingService.getPrintingsByRarity(rarity).stream()
                .map(CardPrintingDTO::new)
                .collect(Collectors.toList());
    }

    // === GET PRINTING BY SERIAL NUMBER ===
    @GetMapping("/serial/{serialNumber}")
    @Operation(summary = "Get printing by serial number")
    public ResponseEntity<CardPrintingDTO> getPrintingBySerialNumber(
            @PathVariable("serialNumber") @Parameter(description = "Serial number", example = "LGN-001") String serialNumber) {
        return cardPrintingService.getPrintingBySerialNumber(serialNumber)
                .map(cardPrinting -> ResponseEntity.ok(new CardPrintingDTO(cardPrinting)))
                .orElse(ResponseEntity.notFound().build());
    }
    
    // === CREATE CARD PRINTING ===
    @PostMapping
    @Operation(summary = "Create a new card printing")
    public ResponseEntity<CardPrintingResponse> createCardPrinting(
            @RequestBody @Parameter(description = "Card printing creation data") CreateCardPrintingRequest createCardPrintingRequest) {
        CardPrinting cardPrinting = createCardPrintingRequest.toCardPrinting();
        CardPrinting savedPrinting = cardPrintingService.saveCardPrinting(cardPrinting);
        CardPrintingResponse response = new CardPrintingResponse(savedPrinting);
        
        return ResponseEntity
                .created(URI.create("/api/card-printings/" + savedPrinting.getId()))
                .body(response);
    }
    
    // === UPDATE CARD PRINTING ===
    @PutMapping("/{id}")
    @Operation(summary = "Update a card printing")
    public ResponseEntity<CardPrintingResponse> updateCardPrinting(
            @PathVariable("id") @Parameter(description = "Card printing ID", example = "1") Long id, 
            @RequestBody @Parameter(description = "Card printing update data") UpdateCardPrintingRequest updateCardPrintingRequest) {
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
    @Operation(summary = "Delete a card printing")
    public ResponseEntity<Void> deleteCardPrinting(
            @PathVariable("id") @Parameter(description = "Card printing ID", example = "1") Long id) {
        cardPrintingService.deleteCardPrinting(id);
        return ResponseEntity.noContent().build();
    }
    
    // === GET PRICE HISTORY ===
    @GetMapping("/{id}/price-history")
    @Operation(summary = "Get price history for a card printing")
    public List<CardPriceDTO> getPriceHistory(
            @PathVariable("id") @Parameter(description = "Card printing ID", example = "1") Long printingId) {
        return cardPrintingService.getPriceHistory(printingId).stream()
                .map(CardPriceDTO::new)
                .collect(Collectors.toList());
    }
    
    // === ADD PRICE TO PRINTING ===
    @PostMapping("/{id}/prices")
    @Operation(summary = "Add price to card printing")
    public ResponseEntity<CardPrintingResponse> addPriceToPrinting(
        @PathVariable("id") @Parameter(description = "Card printing ID", example = "1") Long printingId, 
        @RequestBody @Parameter(description = "Price data") CreateCardPriceRequest createCardPriceRequest) {
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