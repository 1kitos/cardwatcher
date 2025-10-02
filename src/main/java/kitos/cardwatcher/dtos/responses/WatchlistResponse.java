// kitos.cardwatcher.dtos.responses.WatchlistResponse.java
package kitos.cardwatcher.dtos.responses;

import kitos.cardwatcher.entities.Watchlist;
import kitos.cardwatcher.dtos.shared.CardPrintingDTO;
import java.util.List;
import java.util.stream.Collectors;

public class WatchlistResponse {
    private Long id;
    private String name;
    private Integer refreshRate;
    private Long userId;
    private List<CardPrintingDTO> cardPrintings;
    
    public WatchlistResponse(Watchlist watchlist) {
        this.id = watchlist.getId();
        this.name = watchlist.getName();
        this.refreshRate = watchlist.getRefreshRate();
        if (watchlist.getUser() != null) {
            this.userId = watchlist.getUser().getId();
        }
        if (watchlist.getCardPrintings() != null) {
            this.cardPrintings = watchlist.getCardPrintings().stream()
                .map(CardPrintingDTO::new)
                .collect(Collectors.toList());
        }
    }
    
    // Getters
    public Long getId() 
    { 
    	return id;
    }
    
    public String getName() 
    { 
    	return name; 
    }
    
    public Integer getRefreshRate() 
    { 
    	return refreshRate;
    }
    
    public Long getUserId() 
    { 
    	return userId;
    }
    
    public List<CardPrintingDTO> getCardPrintings() 
    { 
    	return cardPrintings;
    }

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRefreshRate(Integer refreshRate) {
		this.refreshRate = refreshRate;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setCardPrintings(List<CardPrintingDTO> cardPrintings) {
		this.cardPrintings = cardPrintings;
	}
    
    
    
}