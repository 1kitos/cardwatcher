package kitos.cardwatcher.dtos.requests;

import kitos.cardwatcher.entities.CardPrinting;

public class CreateCardPrintingRequest {
    private Long cardId;
    private String setCode;
    private String rarity;
    private String serialNumber;
    
    public CreateCardPrintingRequest() {}
    
    // Getters and setters
    public Long getCardId() { return cardId; }
    public void setCardId(Long cardId) { this.cardId = cardId; }
    
    public String getSetCode() { return setCode; }
    public void setSetCode(String setCode) { this.setCode = setCode; }
    
    public String getRarity() { return rarity; }
    public void setRarity(String rarity) { this.rarity = rarity; }
    
    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }
    
    public CardPrinting toCardPrinting() {
        CardPrinting printing = new CardPrinting();
        printing.setSetCode(this.setCode);
        printing.setRarity(this.rarity);
        printing.setSerialNumber(this.serialNumber);
        return printing;
    }
}