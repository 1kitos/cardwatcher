package kitos.cardwatcher.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class User {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String username;
    
    @OneToMany(mappedBy = "user")
    private List<Watchlist> watchlists; // User has multiple watchlists


	public User() 
	{
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Watchlist> getWatchlists() {
		return watchlists;
	}

	public void setWatchlists(List<Watchlist> watchlists) {
		this.watchlists = watchlists;
	}


	
	
	
	
	
}
