package kitos.cardwatcher.dtos.requests;

public class CreateWatchlistRequest 
{
    private String name;
    private Integer refreshRate = 24;
    private Long userId;
    
    public CreateWatchlistRequest() {}
    
    public CreateWatchlistRequest(String name, Integer refreshRate, Long userId) 
    {
        this.name = name;
        this.refreshRate = refreshRate;
        this.userId = userId;
    }
    
    // Getters and setters
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