package kitos.cardwatcher.dtos.shared;

import kitos.cardwatcher.entities.CardGame;

public class CardGameDTO 
{
	
	
	private Long id;
	private String name;
	
	
	public CardGameDTO(CardGame cardGame)
	{
		this.id = cardGame.getId();
		this.name = cardGame.getName();
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
	
	
	
	
	
	
	
	
	
	
}
