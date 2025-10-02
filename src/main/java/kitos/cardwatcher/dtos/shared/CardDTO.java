package kitos.cardwatcher.dtos.shared;

import kitos.cardwatcher.entities.Card;
import java.util.List;
import java.util.stream.Collectors;

public class CardDTO {
    private Long id;
    private String name;
    private CardGameDTO cardGame;
    private List<CardPrintingDTO> cardPrintings;
    
    public CardDTO(Card card) {
        this.id = card.getId();
        this.name = card.getName();
        if (card.getCardGame() != null) {
            this.cardGame = new CardGameDTO(card.getCardGame());
        }
        // Only include basic printing info to avoid infinite recursion
        if (card.getCardPrintings() != null) {
            this.cardPrintings = card.getCardPrintings().stream()
                .map(CardPrintingDTO::new)  // Use the proper constructor
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
    
    public CardGameDTO getCardGame() {
        return cardGame;
    }
    
    public void setCardGame(CardGameDTO cardGame) {
        this.cardGame = cardGame;
    }
    
    public List<CardPrintingDTO> getCardPrintings() {
        return cardPrintings;
    }
    
    public void setCardPrintings(List<CardPrintingDTO> cardPrintings) {
        this.cardPrintings = cardPrintings;
    }
}