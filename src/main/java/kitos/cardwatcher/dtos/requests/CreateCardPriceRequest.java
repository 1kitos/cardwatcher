package kitos.cardwatcher.dtos.requests;

import kitos.cardwatcher.entities.CardPrice;
import java.time.LocalDateTime;

public class CreateCardPriceRequest {
    private Long cardPrintingId;
    private LocalDateTime timestamp;
    private float priceTrend;
    private float priceAverage;
    private float priceLow;
    
    public CreateCardPriceRequest() {}
    
    // Getters and setters
    public Long getCardPrintingId() { return cardPrintingId; }
    public void setCardPrintingId(Long cardPrintingId) { this.cardPrintingId = cardPrintingId; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public float getPriceTrend() { return priceTrend; }
    public void setPriceTrend(float priceTrend) { this.priceTrend = priceTrend; }
    
    public float getPriceAverage() { return priceAverage; }
    public void setPriceAverage(float priceAverage) { this.priceAverage = priceAverage; }
    
    public float getPriceLow() { return priceLow; }
    public void setPriceLow(float priceLow) { this.priceLow = priceLow; }
    
    public CardPrice toCardPrice() {
        CardPrice cardPrice = new CardPrice();
        cardPrice.setTimestamp(this.timestamp);
        cardPrice.setPrice_trend(this.priceTrend);
        cardPrice.setPrice_average(this.priceAverage);
        cardPrice.setPrice_low(this.priceLow);
        return cardPrice;
    }
}