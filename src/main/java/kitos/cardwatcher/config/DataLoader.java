package kitos.cardwatcher.config;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import kitos.cardwatcher.entities.Card;
import kitos.cardwatcher.entities.CardGame;
import kitos.cardwatcher.entities.CardPrice;
import kitos.cardwatcher.entities.CardPrinting;
import kitos.cardwatcher.entities.User;
import kitos.cardwatcher.entities.UserCredentials;
import kitos.cardwatcher.entities.Watchlist;
import kitos.cardwatcher.repositories.CardGameRepository;
import kitos.cardwatcher.repositories.CardPriceRepository;
import kitos.cardwatcher.repositories.CardPrintingRepository;
import kitos.cardwatcher.repositories.CardRepository;
import kitos.cardwatcher.repositories.UserCredentialsRepository;
import kitos.cardwatcher.repositories.UserRepository;
import kitos.cardwatcher.repositories.WatchlistRepository;

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
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserCredentialsRepository userCredentialsRepository;
    
    @Autowired
    private WatchlistRepository watchlistRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        clearData();
        loadCardGames();
        loadCards();
        loadCardPrintings();
        loadCardPrices();
        loadUsersAndCredentials(); // Updated method
        loadUsersAndWatchlists();
        populateWatchlistsWithCards();
    }
    
    private void clearData() {
        cardPriceRepo.deleteAll();
        cardPrintingRepo.deleteAll();
        cardRepo.deleteAll();
        cardGameRepo.deleteAll();
        watchlistRepository.deleteAll(); 
        userCredentialsRepository.deleteAll(); // Clear credentials first
        userRepository.deleteAll();     
    }
    
    public void loadCardGames() {
        if (cardGameRepo.count() == 0) {
            List<CardGame> games = List.of(
                createCardGame("Magic: The Gathering"),
                createCardGame("Pokémon TCG"), 
                createCardGame("Yugioh")
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
            
            CardGame Yugioh = cardGameRepo.findByName("Yugioh")
                .orElseThrow(() -> new RuntimeException("Yugioh not found"));

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
            
            List<Card> YugiohCards = List.of(
                createCard("Sky Striker Ace - Raye", Yugioh),
                createCard("S:P Little Knight", Yugioh),
                createCard("Dominus Impulse", Yugioh)
            );
            
            cardRepo.saveAll(mtgCards);
            cardRepo.saveAll(pokemonCards);
            cardRepo.saveAll(YugiohCards);
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
            
            // Get Yugioh cards
            Card raye = cardRepo.findByNameAndCardGame("Sky Striker Ace - Raye", 
                cardGameRepo.findByName("Yugioh").orElseThrow());
            Card littleKnight = cardRepo.findByNameAndCardGame("S:P Little Knight", 
                cardGameRepo.findByName("Yugioh").orElseThrow());
            Card dominus = cardRepo.findByNameAndCardGame("Dominus Impulse", 
                cardGameRepo.findByName("Yugioh").orElseThrow());

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
            
            // Create printings for Yugioh cards
            List<CardPrinting> YugiohPrintings = List.of(
                createPrinting(raye, "BLHR", "Ultra Rare", "001"),
                createPrinting(raye, "DASA", "Super Rare", "001"),
                createPrinting(littleKnight, "AGOV", "Quarter Century", "001"),
                createPrinting(littleKnight, "AGOV", "Ultra Rare", "001"),
                createPrinting(dominus, "AGOV", "Secret Rare", "002"),
                createPrinting(dominus, "AGOV", "Ultra Rare", "002")
            );
            
            cardPrintingRepo.saveAll(mtgPrintings);
            cardPrintingRepo.saveAll(pokemonPrintings);
            cardPrintingRepo.saveAll(YugiohPrintings);
            
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
    
    // NEW METHOD: Load users with their credentials
    public void loadUsersAndCredentials() {
        if (userRepository.count() == 0) {
            // Create sample users
            List<User> users = List.of(
                createUser("cardCollector"),
                createUser("mtgInvestor"),
                createUser("YugiohFan")
            );
            userRepository.saveAll(users);
            System.out.println("Users loaded!");
            
            // Create credentials for each user
            List<UserCredentials> credentials = List.of(
                createUserCredentials(users.get(0).getId(), "password123"),
                createUserCredentials(users.get(1).getId(), "investor456"),
                createUserCredentials(users.get(2).getId(), "Yugioh789")
            );
            userCredentialsRepository.saveAll(credentials);
            System.out.println("User credentials loaded!");
        }
    }
    
    public void loadUsersAndWatchlists() {
        if (watchlistRepository.count() == 0) {
            // Get users (already created in loadUsersAndCredentials)
            User collector = userRepository.findByUsername("cardCollector").orElseThrow();
            User investor = userRepository.findByUsername("mtgInvestor").orElseThrow();
            User YugiohFan = userRepository.findByUsername("YugiohFan").orElseThrow();
            
            List<Watchlist> watchlists = List.of(
                createWatchlist("Favorites", 24, collector),
                createWatchlist("Investment Watch", 6, investor),
                createWatchlist("Budget Buys", 12, collector),
                createWatchlist("Yugioh Collection", 24, YugiohFan)
            );
            watchlistRepository.saveAll(watchlists);
            System.out.println("Watchlists loaded!");
        }
    }

    private void populateWatchlistsWithCards() {
        try {
            // Get users
            User collector = userRepository.findByUsername("cardCollector").orElseThrow();
            User investor = userRepository.findByUsername("mtgInvestor").orElseThrow();
            User YugiohFan = userRepository.findByUsername("YugiohFan").orElseThrow();
            
            // Get all printings
            List<CardPrinting> allPrintings = cardPrintingRepo.findAll();
            
            // Get specific printings
            List<CardPrinting> expensivePrintings = allPrintings.stream()
                .filter(p -> p.getRarity().toLowerCase().contains("mythic") || 
                            p.getRarity().toLowerCase().contains("secret") ||
                            p.getRarity().toLowerCase().contains("rainbow"))
                .limit(4)
                .collect(Collectors.toList());
            
            List<CardPrinting> budgetPrintings = allPrintings.stream()
                .filter(p -> p.getRarity().toLowerCase().contains("common") || 
                            p.getRarity().toLowerCase().contains("uncommon"))
                .limit(3)
                .collect(Collectors.toList());
            
            List<CardPrinting> YugiohPrintings = allPrintings.stream()
                .filter(p -> p.getCard().getCardGame().getName().equals("Yugioh"))
                .limit(5)
                .collect(Collectors.toList());
            
            List<CardPrinting> favoritePrintings = allPrintings.stream()
                .filter(p -> p.getCard().getName().contains("Umbreon") || 
                            p.getCard().getName().contains("Atraxa") ||
                            p.getCard().getName().contains("Raye"))
                .limit(3)
                .collect(Collectors.toList());
            
            // SIMPLE APPROACH: Use native queries to populate the join table
            populateWatchlistDirectly("Favorites", collector.getId(), favoritePrintings);
            populateWatchlistDirectly("Investment Watch", investor.getId(), expensivePrintings);
            populateWatchlistDirectly("Budget Buys", collector.getId(), budgetPrintings);
            populateWatchlistDirectly("Yugioh Collection", YugiohFan.getId(), YugiohPrintings);
            
            System.out.println("Watchlists populated with card printings!");
        } catch (Exception e) {
            System.err.println("Error populating watchlists: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Helper method to directly populate the relationship
    private void populateWatchlistDirectly(String watchlistName, Long userId, List<CardPrinting> printings) {
        // Find the watchlist
        List<Watchlist> userWatchlists = watchlistRepository.findByUserId(userId);
        Watchlist watchlist = userWatchlists.stream()
            .filter(w -> w.getName().equals(watchlistName))
            .findFirst()
            .orElseThrow(() -> new RuntimeException(watchlistName + " not found for user " + userId));
        
        // Clear existing relationships
        watchlist.getCardPrintings().clear();
        watchlistRepository.saveAndFlush(watchlist);
        
        // Add new cards
        if (!printings.isEmpty()) {
            watchlist.getCardPrintings().addAll(printings);
            watchlistRepository.saveAndFlush(watchlist);
            System.out.println(watchlistName + " populated with " + printings.size() + " cards");
        }
    }

    private CardPrice createPriceForPrinting(CardPrinting printing) {
        CardPrice price = new CardPrice();
        price.setCardPrinting(printing);
        price.setTimestamp(LocalDateTime.now().minusDays((long) (Math.random() * 30)));
        
        float basePrice = getBasePriceByRarity(printing.getRarity());
        float variation = (float) (Math.random() * 0.4 * basePrice);
        
        price.setPriceTrend(roundToTwoDecimals(basePrice + variation * 0.1f));
        price.setPriceAverage(roundToTwoDecimals(basePrice + variation * 0.05f));
        price.setPriceLow(roundToTwoDecimals(basePrice - variation * 0.15f));
        
        return price;
    }

    private float roundToTwoDecimals(float value) {
        return Math.round(value * 100.0f) / 100.0f;
    }

    private float getBasePriceByRarity(String rarity) {
        float price = switch (rarity.toLowerCase()) {
            case "common", "uncommon" -> 0.10f + (float) (Math.random() * 0.40f);
            case "rare", "super rare", "holo" -> 1.00f + (float) (Math.random() * 4.00f);
            case "mythic", "ultra rare", "full art" -> 5.00f + (float) (Math.random() * 15.00f);
            case "secret rare", "rainbow rare", "quarter century" -> 20.00f + (float) (Math.random() * 80.00f);
            case "foil", "1st edition" -> 2.00f + (float) (Math.random() * 8.00f);
            default -> 0.50f + (float) (Math.random() * 2.00f);
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
    
    // NEW METHOD: Create user credentials
    private UserCredentials createUserCredentials(Long userId, String plainPassword) {
        UserCredentials credentials = new UserCredentials();
        credentials.setUserId(userId);
        credentials.setEncryptedPassword(passwordEncoder.encode(plainPassword));
        return credentials;
    }

    private User createUser(String username) {
        User user = new User();
        user.setUsername(username);
        return user;
    }

    private Watchlist createWatchlist(String name, Integer refreshRate, User user) {
        Watchlist watchlist = new Watchlist();
        watchlist.setName(name);
        watchlist.setRefreshRate(refreshRate);
        watchlist.setUser(user);
        return watchlist;
    }

    // Helper method to get fresh watchlist entities
    private Watchlist getFreshWatchlist(String name, Long userId) {
        return watchlistRepository.findByUserId(userId)
            .stream()
            .filter(w -> w.getName().equals(name))
            .findFirst()
            .orElseThrow(() -> new RuntimeException(name + " watchlist not found"));
    }

    // Helper method to recreate watchlist with new cards
    private void recreateWatchlistWithCards(Watchlist watchlist, List<CardPrinting> cardPrintings) {
        Watchlist freshWatchlist = watchlistRepository.findById(watchlist.getId()).orElseThrow();
        freshWatchlist.setCardPrintings(new java.util.ArrayList<>());
        watchlistRepository.save(freshWatchlist);
        
        freshWatchlist.getCardPrintings().addAll(cardPrintings);
        watchlistRepository.save(freshWatchlist);
        
        System.out.println(freshWatchlist.getName() + " now has " + freshWatchlist.getCardPrintings().size() + " cards");
    }
}