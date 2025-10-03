package kitos.cardwatcher.dtos.shared;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import kitos.cardwatcher.entities.Watchlist;

public class WatchlistDTO {
    private Long id;
    private String name;
    private Integer refreshRate;
    private Long userId;
    private LocalDateTime createdAt;   
    private LocalDateTime updatedAt; 
    private List<CardPrintingDTO> cardPrintings; // Add this field
    
    public WatchlistDTO(Watchlist watchlist) {
        this.id = watchlist.getId();
        this.name = watchlist.getName();
        this.refreshRate = watchlist.getRefreshRate();
        this.createdAt = watchlist.getCreatedAt();
        this.updatedAt = watchlist.getUpdatedAt();
        
        if (watchlist.getUser() != null) {
            this.userId = watchlist.getUser().getId();
        }
        
        // Add CardPrintingDTO mapping
        if (watchlist.getCardPrintings() != null) {
            this.cardPrintings = watchlist.getCardPrintings().stream()
                .map(CardPrintingDTO::new)
                .collect(Collectors.toList());
        }
    }
    
    // Getters and setters
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
    
    public Integer getRefreshRate() { 
        return refreshRate;
    }
    
    public void setRefreshRate(Integer refreshRate) { 
        this.refreshRate = refreshRate;
    }
    
    public Long getUserId() { 
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId; 
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<CardPrintingDTO> getCardPrintings() {
        return cardPrintings;
    }

    public void setCardPrintings(List<CardPrintingDTO> cardPrintings) {
        this.cardPrintings = cardPrintings;
    }
}