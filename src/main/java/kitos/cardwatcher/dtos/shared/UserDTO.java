// kitos.cardwatcher.dtos.shared.UserDTO.java
package kitos.cardwatcher.dtos.shared;

import kitos.cardwatcher.entities.User;

public class UserDTO {
    private Long id;
    private String username;
    
    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
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
    
    public String getUsername() 
    { 
    	return username;
    }
    
    public void setUsername(String username) 
    { 
    	this.username = username;
    }
    
}