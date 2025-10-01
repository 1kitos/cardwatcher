package kitos.cardwatcher.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "card_games")
public class CardGame {
	
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
//    private String iconUrl;
    
    
    
    public CardGame()
    {
    	
    }
    
    public CardGame(String name) 
    {
        this.name = name;
    }
    
//    public CardGame(String name, String iconUrl) {
//        this.name = name;
//        this.iconUrl = iconUrl;
//    }
    
    
    
    public Long getId() 
    {
		return id;
	}





	public void setId(Long id) {
		this.id = id;
	}





	public String getName()
    {
    	return this.name;
    }
    
    public void setName(String name)
    {
    	this.name = name;
    }
    
    
    
    
    
    
}
