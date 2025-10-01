package kitos.cardwatcher.entities;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "card_game_id")
    private CardGame cardGame;
    
    @OneToMany(mappedBy = "card")
    private List<CardPrinting> cardPrintings = new ArrayList<>();
    

	public Card() {
		super();
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public CardGame getCardGame() {
		return cardGame;
	}

	public void setCardGame(CardGame cardGame) {
		this.cardGame = cardGame;
	}
	
	
	public List<CardPrinting> getCardPrintings()
	{
		return this.cardPrintings;
	}
	
	
	public void setCardPrintings(List<CardPrinting> printings)
	{
		this.cardPrintings = printings;
	}
    
    
    
    
    
    
    
    
    
}