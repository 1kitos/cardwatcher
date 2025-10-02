package kitos.cardwatcher.dtos.responses;

import kitos.cardwatcher.entities.CardPrinting;

public class CardPrintingResponse {
    private Long id;
    private String cardName;
    private Long cardId;
    private String setCode;
    private String rarity;
    private String serialNumber;
    
    public CardPrintingResponse(CardPrinting entity) {
        this.id = entity.getId();
        this.setCode = entity.getSetCode();
        this.rarity = entity.getRarity();
        this.serialNumber = entity.getSerialNumber();
        
        if (entity.getCard() != null) {
            this.cardName = entity.getCard().getName();
            this.cardId = entity.getCard().getId();
        }
    }
    
    // Getters
    public Long getId() { return id; }
    public String getCardName() { return cardName; }
    public Long getCardId() { return cardId; }
    public String getSetCode() { return setCode; }
    public String getRarity() { return rarity; }
    public String getSerialNumber() { return serialNumber; }
}