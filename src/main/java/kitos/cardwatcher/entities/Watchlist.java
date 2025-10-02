package kitos.cardwatcher.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Watchlist {
	
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    
	    private String name;
	    
	    @ManyToOne
	    @JoinColumn(name = "user_id")
	    private User user;
	    
	    private Integer refreshRate = 24;
	    
	    @ManyToMany
	    @JoinTable(
	        name = "watchlist_card_printings",
	        joinColumns = @JoinColumn(name = "watchlist_id"),
	        inverseJoinColumns = @JoinColumn(name = "card_printing_id")
	    )
	    private List<CardPrinting> cardPrintings;
    
    

	public Watchlist() {
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


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Integer getRefreshRate() {
		return refreshRate;
	}


	public void setRefreshRate(Integer refreshRate) {
		this.refreshRate = refreshRate;
	}


	public List<CardPrinting> getCardPrintings() {
		return cardPrintings;
	}


	public void setCardPrintings(List<CardPrinting> cardPrintings) {
		this.cardPrintings = cardPrintings;
	}
    
    
    
    
}