package kitos.cardwatcher.dtos.responses;

import java.time.LocalDateTime;

import kitos.cardwatcher.entities.CardPrice;

public class CardPriceResponse {
    private Long id;
    private Long cardPrintingId;
    private LocalDateTime timestamp;
    private float priceTrend;
    private float priceAverage;
    private float priceLow;
    
    public CardPriceResponse(CardPrice entity) {
        this.id = entity.getId();
        this.timestamp = entity.getTimestamp();
        this.priceTrend = entity.getPrice_trend();
        this.priceAverage = entity.getPrice_average();
        this.priceLow = entity.getPrice_low();
        
        if (entity.getCardPrinting() != null) {
            this.cardPrintingId = entity.getCardPrinting().getId();
        }
    }
    
    // Getters
    public Long getId() { return id; }
    public Long getCardPrintingId() { return cardPrintingId; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public float getPriceTrend() { return priceTrend; }
    public float getPriceAverage() { return priceAverage; }
    public float getPriceLow() { return priceLow; }
}