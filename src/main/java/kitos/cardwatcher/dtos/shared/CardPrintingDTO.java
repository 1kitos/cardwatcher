package kitos.cardwatcher.dtos.shared;

import kitos.cardwatcher.entities.CardPrinting;
import java.util.List;
import java.util.stream.Collectors;

public class CardPrintingDTO {
    private Long id;
    private String cardName;
    private Long cardId;
    private String setCode;
    private String rarity;
    private String serialNumber;
    private List<CardPriceDTO> priceHistory;
    
    public CardPrintingDTO(CardPrinting cardPrinting) {
        this.id = cardPrinting.getId();
        this.setCode = cardPrinting.getSetCode();
        this.rarity = cardPrinting.getRarity();
        this.serialNumber = cardPrinting.getSerialNumber();
        
        if (cardPrinting.getCard() != null) {
            this.cardName = cardPrinting.getCard().getName();
            this.cardId = cardPrinting.getCard().getId();
        }
        
        // Include price history using the correct CardPriceDTO constructor
        if (cardPrinting.getPriceHistory() != null) {
            this.priceHistory = cardPrinting.getPriceHistory().stream()
                .map(CardPriceDTO::new)  // Use the constructor that takes CardPrice entity
                .collect(Collectors.toList());
        }
    }
    
    // Getters and setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCardName() {
        return cardName;
    }
    
    public void setCardName(String cardName) {
        this.cardName = cardName;
    }
    
    public Long getCardId() {
        return cardId;
    }
    
    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }
    
    public String getSetCode() {
        return setCode;
    }
    
    public void setSetCode(String setCode) {
        this.setCode = setCode;
    }
    
    public String getRarity() {
        return rarity;
    }
    
    public void setRarity(String rarity) {
        this.rarity = rarity;
    }
    
    public String getSerialNumber() {
        return serialNumber;
    }
    
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
    
    public List<CardPriceDTO> getPriceHistory() {
        return priceHistory;
    }
    
    public void setPriceHistory(List<CardPriceDTO> priceHistory) {
        this.priceHistory = priceHistory;
    }
}