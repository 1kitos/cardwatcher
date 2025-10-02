package kitos.cardwatcher.dtos.requests;

import kitos.cardwatcher.entities.Card;

public class CreateCardRequest {
    private String name;
    private Long cardGameId;
    
    public CreateCardRequest() {}
    
    public CreateCardRequest(String name, Long cardGameId) {
        this.name = name;
        this.cardGameId = cardGameId;
    }
    
    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public Long getCardGameId() { return cardGameId; }
    public void setCardGameId(Long cardGameId) { this.cardGameId = cardGameId; }
    
    public Card toCard() {
        Card card = new Card();
        card.setName(this.name);
        // You'll need to set the CardGame relationship in service or here
        return card;
    }
}