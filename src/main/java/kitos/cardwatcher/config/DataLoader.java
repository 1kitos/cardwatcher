package kitos.cardwatcher.config;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import kitos.cardwatcher.entities.Card;
import kitos.cardwatcher.entities.CardGame;
import kitos.cardwatcher.entities.CardPrice;
import kitos.cardwatcher.entities.CardPrinting;
import kitos.cardwatcher.repositories.CardGameRepository;
import kitos.cardwatcher.repositories.CardPriceRepository;
import kitos.cardwatcher.repositories.CardPrintingRepository;
import kitos.cardwatcher.repositories.CardRepository;

@Component
public class DataLoader implements CommandLineRunner {
    
    @Autowired
    private CardGameRepository cardGameRepo;
    
    @Autowired 
    private CardRepository cardRepo;
    
    @Autowired
    private CardPrintingRepository cardPrintingRepo;
    
    @Autowired
    private CardPriceRepository cardPriceRepo;

    @Override
    public void run(String... args) throws Exception {
//        clearData();
        loadCardGames();
        loadCards();
        loadCardPrintings();
        loadCardPrices();
    }
    
    private void clearData() {
        cardPriceRepo.deleteAll();
        cardPrintingRepo.deleteAll();
        cardRepo.deleteAll();
        cardGameRepo.deleteAll();
    }
    
    public void loadCardGames() {
        if (cardGameRepo.count() == 0) {
            List<CardGame> games = List.of(
                createCardGame("Magic: The Gathering"),
                createCardGame("Pokémon TCG"), 
                createCardGame("Yu-Gi-Oh")
            );
            cardGameRepo.saveAll(games);
            System.out.println("Card games loaded!");
        }
    }
    
    public void loadCards() {
        if (cardRepo.count() == 0) {
            CardGame mtg = cardGameRepo.findByName("Magic: The Gathering")
                .orElseThrow(() -> new RuntimeException("MTG not found"));
            
            CardGame pokemon = cardGameRepo.findByName("Pokémon TCG")
                .orElseThrow(() -> new RuntimeException("Pokemon not found"));
            
            CardGame yugioh = cardGameRepo.findByName("Yu-Gi-Oh")
                .orElseThrow(() -> new RuntimeException("YuGiOh not found"));

            // Create MTG cards
            List<Card> mtgCards = List.of(
                createCard("Ajani's Pridesmate", mtg),
                createCard("Sephiroth, Fabled SOLDIER // Sephiroth, One-Winged Angel", mtg),
                createCard("Atraxa, Grand Unifier", mtg)
            );
            
            List<Card> pokemonCards = List.of(
                createCard("Umbreon VMAX", pokemon),
                createCard("Froslass", pokemon), 
                createCard("Chikorita", pokemon)
            );
            
            List<Card> yugiohCards = List.of(
                createCard("Sky Striker Ace - Raye", yugioh),
                createCard("S:P Little Knight", yugioh),
                createCard("Dominus Impulse", yugioh)
            );
            
            cardRepo.saveAll(mtgCards);
            cardRepo.saveAll(pokemonCards);
            cardRepo.saveAll(yugiohCards);
        }
    }
    
    public void loadCardPrintings() {
        if (cardPrintingRepo.count() == 0) {
            // Get MTG cards
            Card ajanisPridesmate = cardRepo.findByNameAndCardGame("Ajani's Pridesmate", 
                cardGameRepo.findByName("Magic: The Gathering").orElseThrow());
            Card sephiroth = cardRepo.findByNameAndCardGame("Sephiroth, Fabled SOLDIER // Sephiroth, One-Winged Angel", 
                cardGameRepo.findByName("Magic: The Gathering").orElseThrow());
            Card atraxa = cardRepo.findByNameAndCardGame("Atraxa, Grand Unifier", 
                cardGameRepo.findByName("Magic: The Gathering").orElseThrow());
            
            // Get Pokémon cards
            Card umbreon = cardRepo.findByNameAndCardGame("Umbreon VMAX", 
                cardGameRepo.findByName("Pokémon TCG").orElseThrow());
            Card froslass = cardRepo.findByNameAndCardGame("Froslass", 
                cardGameRepo.findByName("Pokémon TCG").orElseThrow());
            Card chikorita = cardRepo.findByNameAndCardGame("Chikorita", 
                cardGameRepo.findByName("Pokémon TCG").orElseThrow());
            
            // Get Yu-Gi-Oh cards
            Card raye = cardRepo.findByNameAndCardGame("Sky Striker Ace - Raye", 
                cardGameRepo.findByName("Yu-Gi-Oh").orElseThrow());
            Card littleKnight = cardRepo.findByNameAndCardGame("S:P Little Knight", 
                cardGameRepo.findByName("Yu-Gi-Oh").orElseThrow());
            Card dominus = cardRepo.findByNameAndCardGame("Dominus Impulse", 
                cardGameRepo.findByName("Yu-Gi-Oh").orElseThrow());

            // Create printings for MTG cards
            List<CardPrinting> mtgPrintings = List.of(
                createPrinting(ajanisPridesmate, "M21", "Uncommon", "027"),
                createPrinting(ajanisPridesmate, "A25", "Uncommon", "005"),
                createPrinting(sephiroth, "FFL", "Mythic", "018"),
                createPrinting(sephiroth, "FFL", "Foil", "018F"),
                createPrinting(atraxa, "ONE", "Mythic", "001"),
                createPrinting(atraxa, "PONE", "Mythic", "001P")
            );
            
            // Create printings for Pokémon cards
            List<CardPrinting> pokemonPrintings = List.of(
                createPrinting(umbreon, "EVS", "Rainbow Rare", "095"),
                createPrinting(umbreon, "EVS", "Full Art", "095"),
                createPrinting(froslass, "TWM", "Rare", "174"),
                createPrinting(froslass, "TWM", "Holo", "174H"),
                createPrinting(chikorita, "NG", "Common", "054"),
                createPrinting(chikorita, "NG", "1st Edition", "0541")
            );
            
            // Create printings for Yu-Gi-Oh cards
            List<CardPrinting> yugiohPrintings = List.of(
                createPrinting(raye, "BLHR", "Ultra Rare", "001"),
                createPrinting(raye, "DASA", "Super Rare", "001"),
                createPrinting(littleKnight, "AGOV", "Quarter Century", "001"),
                createPrinting(littleKnight, "AGOV", "Ultra Rare", "001"),
                createPrinting(dominus, "AGOV", "Secret Rare", "002"),
                createPrinting(dominus, "AGOV", "Ultra Rare", "002")
            );
            
            cardPrintingRepo.saveAll(mtgPrintings);
            cardPrintingRepo.saveAll(pokemonPrintings);
            cardPrintingRepo.saveAll(yugiohPrintings);
            
            System.out.println("Card printings loaded!");
        }
    }
    
    public void loadCardPrices() {
        if (cardPriceRepo.count() == 0) {
            // Get all printings
            List<CardPrinting> allPrintings = cardPrintingRepo.findAll();
            
            // Create one price for each printing
            List<CardPrice> prices = allPrintings.stream()
                .map(this::createPriceForPrinting)
                .toList();
            
            cardPriceRepo.saveAll(prices);
            System.out.println("Card prices loaded!");
        }
    }
    
    private CardPrice createPriceForPrinting(CardPrinting printing) {
        CardPrice price = new CardPrice();
        price.setCardPrinting(printing);
        price.setTimestamp(LocalDateTime.now().minusDays((long) (Math.random() * 30)));
        
        // Generate realistic price ranges based on rarity
        float basePrice = getBasePriceByRarity(printing.getRarity());
        float variation = (float) (Math.random() * 0.4 * basePrice); // ±20% variation
        
        // Round all price fields to 2 decimal places
        price.setPrice_trend(roundToTwoDecimals(basePrice + variation * 0.1f));
        price.setPrice_average(roundToTwoDecimals(basePrice + variation * 0.05f));
        price.setPrice_low(roundToTwoDecimals(basePrice - variation * 0.15f));
        
        return price;
    }

    private float roundToTwoDecimals(float value) {
        return Math.round(value * 100.0f) / 100.0f;
    }

    private float getBasePriceByRarity(String rarity) {
        float price = switch (rarity.toLowerCase()) {
            case "common", "uncommon" -> 0.10f + (float) (Math.random() * 0.40f); // $0.10 - $0.50
            case "rare", "super rare", "holo" -> 1.00f + (float) (Math.random() * 4.00f); // $1 - $5
            case "mythic", "ultra rare", "full art" -> 5.00f + (float) (Math.random() * 15.00f); // $5 - $20
            case "secret rare", "rainbow rare", "quarter century" -> 20.00f + (float) (Math.random() * 80.00f); // $20 - $100
            case "foil", "1st edition" -> 2.00f + (float) (Math.random() * 8.00f); // $2 - $10
            default -> 0.50f + (float) (Math.random() * 2.00f); // $0.50 - $2.50
        };
        
        return roundToTwoDecimals(price);
    }

    private CardGame createCardGame(String name) {
        CardGame game = new CardGame();
        game.setName(name);
        return game;
    }

    private Card createCard(String name, CardGame game) {
        Card card = new Card();
        card.setName(name);
        card.setCardGame(game);
        return card;
    }
    
    private CardPrinting createPrinting(Card card, String setCode, String rarity, String serialNumber) {
        CardPrinting printing = new CardPrinting();
        printing.setCard(card);
        printing.setSetCode(setCode);
        printing.setRarity(rarity);
        printing.setSerialNumber(serialNumber);
        return printing;
    }
}