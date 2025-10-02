// kitos.cardwatcher.dtos.shared.WatchlistDTO.java
package kitos.cardwatcher.dtos.shared;

import kitos.cardwatcher.entities.Watchlist;

public class WatchlistDTO {
    private Long id;
    private String name;
    private Integer refreshRate;
    private Long userId;
    
    public WatchlistDTO(Watchlist watchlist) {
        this.id = watchlist.getId();
        this.name = watchlist.getName();
        this.refreshRate = watchlist.getRefreshRate();
        if (watchlist.getUser() != null) {
            this.userId = watchlist.getUser().getId();
        }
    }
    
    // Getters and setters
    public Long getId() 
    { 
    	return id;
    }
    
    public void setId(Long id) 
    { 
    	this.id = id; 
    }
    
    public String getName() 
    { 
    	return name;
    }
    
    public void setName(String name) 
    { 
    	this.name = name;
    }
    
    public Integer getRefreshRate() 
    { 
    	return refreshRate;
    }
    
    public void setRefreshRate(Integer refreshRate) 
    { 
    	this.refreshRate = refreshRate;
    }
    
    public Long getUserId() 
    { 
    	return userId;
    }
    
    public void setUserId(Long userId) 
    {
    	this.userId = userId; 
    }
}