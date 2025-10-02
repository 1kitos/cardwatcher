package kitos.cardwatcher.dtos.responses;

import kitos.cardwatcher.entities.CardGame;

public class CardGameResponse {
    private Long id;
    private String name;
    
    public CardGameResponse(CardGame entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }
    
    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
}