package kitos.cardwatcher.dtos.requests;

import kitos.cardwatcher.entities.CardGame;

public class UpdateCardGameRequest {
    private String name;
    
    public UpdateCardGameRequest() {}
    
    public UpdateCardGameRequest(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public CardGame toCardGame() {
        CardGame game = new CardGame();
        game.setName(this.name);
        return game;
    }
}