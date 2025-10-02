package kitos.cardwatcher.dtos.responses;

import kitos.cardwatcher.entities.User;
import java.util.List;
import java.util.stream.Collectors;

public class UserResponse {
    private Long id;
    private String username;
    private List<WatchlistResponse> watchlists;
    
    public UserResponse(User user) 
    {
        this.id = user.getId();
        this.username = user.getUsername();
        if (user.getWatchlists() != null) {
            this.watchlists = user.getWatchlists().stream()
                .map(WatchlistResponse::new)
                .collect(Collectors.toList());
        }
    }
    
    public Long getId() 
    { 
    	return id;
    }
    
    public String getUsername()
    { 
    	return username;
    }
    
    public List<WatchlistResponse> getWatchlists() 
    { 
    	return watchlists;
    }

	public void setId(Long id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setWatchlists(List<WatchlistResponse> watchlists) {
		this.watchlists = watchlists;
	}
    
    
}