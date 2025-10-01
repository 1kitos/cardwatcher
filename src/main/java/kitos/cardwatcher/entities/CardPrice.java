package kitos.cardwatcher.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "card_prices")
public class CardPrice 
{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	

	@ManyToOne
	@JoinColumn(name = "price_history")
	private CardPrinting cardPrinting;
	
	private LocalDateTime timestamp;
	
	private float price_trend;
	
	private float price_average;
	
	private float price_low;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CardPrinting getCardPrinting() {
		return cardPrinting;
	}

	public void setCardPrinting(CardPrinting cardPrinting) {
		this.cardPrinting = cardPrinting;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public float getPrice_trend() {
		return price_trend;
	}

	public void setPrice_trend(float price_trend) {
		this.price_trend = price_trend;
	}

	public float getPrice_average() {
		return price_average;
	}

	public void setPrice_average(float price_average) {
		this.price_average = price_average;
	}

	public float getPrice_low() {
		return price_low;
	}

	public void setPrice_low(float price_low) {
		this.price_low = price_low;
	}
	
	
	
	
	
	
	
	
	
	
	
}
