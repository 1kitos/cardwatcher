package kitos.cardwatcher.dtos.shared;

import kitos.cardwatcher.entities.CardPrice;
import java.time.LocalDateTime;

public class CardPriceDTO {
    private Long id;
    private Long cardPrintingId;
    private LocalDateTime timestamp;
    private float priceTrend;
    private float priceAverage;
    private float priceLow;
    
    public CardPriceDTO(CardPrice cardPrice) {
        this.id = cardPrice.getId();
        this.timestamp = cardPrice.getTimestamp();
        this.priceTrend = cardPrice.getPriceTrend();
        this.priceAverage = cardPrice.getPriceAverage();
        this.priceLow = cardPrice.getPriceLow();
        
        if (cardPrice.getCardPrinting() != null) {
            this.cardPrintingId = cardPrice.getCardPrinting().getId();
        }
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCardPrintingId() {
		return cardPrintingId;
	}

	public void setCardPrintingId(Long cardPrintingId) {
		this.cardPrintingId = cardPrintingId;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public float getPriceTrend() {
		return priceTrend;
	}

	public void setPriceTrend(float priceTrend) {
		this.priceTrend = priceTrend;
	}

	public float getPriceAverage() {
		return priceAverage;
	}

	public void setPriceAverage(float priceAverage) {
		this.priceAverage = priceAverage;
	}

	public float getPriceLow() {
		return priceLow;
	}

	public void setPriceLow(float priceLow) {
		this.priceLow = priceLow;
	}
    
    
}