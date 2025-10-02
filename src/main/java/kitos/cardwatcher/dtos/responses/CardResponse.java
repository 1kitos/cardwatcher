package kitos.cardwatcher.dtos.responses;

import kitos.cardwatcher.entities.Card;

public class CardResponse {
    private Long id;
    private String name;
    private String cardGameName;
    
    public CardResponse(Card entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.cardGameName = entity.getCardGame() != null ? entity.getCardGame().getName() : null;
    }
    
    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getCardGameName() { return cardGameName; }
}