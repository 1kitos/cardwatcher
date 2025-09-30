package kitos.cardwatcher.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "card_printings")
public class CardPrinting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;
    
    private String setCode;
    private String rarity;
    private String serialNumber;
	
	
	public CardPrinting()
	{
		super();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}
	
	public Card getCard()
	{
		return this.card;
	}
	
	public void setCard(Card card)
	{
		this.card = card;
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
	
	
	
	
	
	
	
}
