package kitos.cardwatcher.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import kitos.cardwatcher.entities.Card;
import kitos.cardwatcher.entities.CardGame;
import kitos.cardwatcher.repositories.CardRepository;
import kitos.cardwatcher.repositories.CardGameRepository;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CardGameRepository cardGameRepository;

    // === GET CARD BY ID ===
    public Optional<Card> getCardById(Long id) {
        return cardRepository.findById(id);
    }

    // === GET CARDS BY CARD GAME ID ===
    public List<Card> getCardsByCardGameId(Long cardGameId) {
        return cardRepository.findByCardGameId(cardGameId);
    }

    // === GET CARDS BY CARD GAME NAME ===
    public List<Card> getCardsByCardGameName(String gameName) {
        Optional<CardGame> cardGame = cardGameRepository.findByName(gameName);
        return cardGame.map(game -> cardRepository.findByCardGameId(game.getId()))
                      .orElse(List.of());
    }

    // === GET CARDS BY CARD GAME NAME (case insensitive) ===
    public List<Card> getCardsByCardGameNameIgnoreCase(String gameName) {
        Optional<CardGame> cardGame = cardGameRepository.findByNameIgnoreCase(gameName);
        return cardGame.map(game -> cardRepository.findByCardGameId(game.getId()))
                      .orElse(List.of());
    }

    // === GET ALL CARDS ===
    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    // === CREATE OR UPDATE CARD ===
    public Card saveCard(Card card) {
        return cardRepository.save(card);
    }

    // === UPDATE CARD ===
    public Card updateCard(Long id, Card cardDetails) {
        return cardRepository.findById(id)
                .map(card -> {
                    card.setName(cardDetails.getName());
                    card.setSetCode(cardDetails.getSetCode());
                    card.setCardNumber(cardDetails.getCardNumber());
                    card.setCardGame(cardDetails.getCardGame());
                    return cardRepository.save(card);
                })
                .orElseThrow(() -> new RuntimeException("Card not found with id: " + id));
    }

    // === DELETE CARD ===
    public void deleteCard(Long id) {
        cardRepository.deleteById(id);
    }
}