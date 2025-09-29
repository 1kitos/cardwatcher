package kitos.cardwatcher.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import kitos.cardwatcher.entities.CardGame;
import kitos.cardwatcher.repositories.CardGameRepository;
import java.util.List;
import java.util.Optional;

@Service
public class CardGameService {

    @Autowired
    private CardGameRepository cardGameRepository;

    // === GET CARD GAME BY ID ===
    public Optional<CardGame> getCardGameById(Long id) {
        return cardGameRepository.findById(id);
    }

    // === GET CARD GAME BY NAME (exact match) ===
    public Optional<CardGame> getCardGameByName(String name) {
        return cardGameRepository.findByName(name);
    }

    // === GET CARD GAME BY NAME (case insensitive) ===
    public Optional<CardGame> getCardGameByNameIgnoreCase(String name) {
        return cardGameRepository.findByNameIgnoreCase(name);
    }

    // === CHECK IF CARD GAME EXISTS ===
    public boolean existsByName(String name) {
        return cardGameRepository.existsByName(name);
    }

    // === GET ALL CARD GAMES ===
    public List<CardGame> getAllCardGames() {
        return cardGameRepository.findAll();
    }

    // === CREATE OR UPDATE CARD GAME ===
    public CardGame saveCardGame(CardGame cardGame) {
        return cardGameRepository.save(cardGame);
    }

    // === UPDATE CARD GAME ===
    public CardGame updateCardGame(Long id, CardGame cardGameDetails) {
        return cardGameRepository.findById(id)
                .map(cardGame -> {
                    cardGame.setName(cardGameDetails.getName());
                    // Add other fields if your CardGame entity has more properties
                    return cardGameRepository.save(cardGame);
                })
                .orElseThrow(() -> new RuntimeException("CardGame not found with id: " + id));
    }

    // === DELETE CARD GAME ===
    public void deleteCardGame(Long id) {
        cardGameRepository.deleteById(id);
    }
}