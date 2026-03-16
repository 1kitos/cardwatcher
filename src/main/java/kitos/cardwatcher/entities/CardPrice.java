package kitos.cardwatcher.entities;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "card_prices")
public class CardPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "card_printing_id")
    private CardPrinting cardPrinting;

    private LocalDateTime timestamp;
    private float priceTrend;
    private float priceAverage;
    private float priceLow;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public CardPrinting getCardPrinting() { return cardPrinting; }
    public void setCardPrinting(CardPrinting cardPrinting) { this.cardPrinting = cardPrinting; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public float getPriceTrend() { return priceTrend; }
    public void setPriceTrend(float priceTrend) { this.priceTrend = priceTrend; }

    public float getPriceAverage() { return priceAverage; }
    public void setPriceAverage(float priceAverage) { this.priceAverage = priceAverage; }

    public float getPriceLow() { return priceLow; }
    public void setPriceLow(float priceLow) { this.priceLow = priceLow; }
}