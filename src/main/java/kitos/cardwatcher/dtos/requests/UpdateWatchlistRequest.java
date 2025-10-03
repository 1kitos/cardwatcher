// kitos.cardwatcher.dtos.requests.UpdateWatchlistRequest.java
package kitos.cardwatcher.dtos.requests;

public class UpdateWatchlistRequest 
{
    private String name;
    private Integer refreshRate;
    
    public UpdateWatchlistRequest() {}
    
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
}