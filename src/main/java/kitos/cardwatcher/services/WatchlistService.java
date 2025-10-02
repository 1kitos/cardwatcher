// kitos.cardwatcher.services.WatchlistService.java
package kitos.cardwatcher.services;

import kitos.cardwatcher.entities.Watchlist;
import kitos.cardwatcher.entities.User;
import kitos.cardwatcher.entities.CardPrinting;
import kitos.cardwatcher.repositories.WatchlistRepository;
import kitos.cardwatcher.repositories.UserRepository;
import kitos.cardwatcher.repositories.CardPrintingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class WatchlistService {

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CardPrintingRepository cardPrintingRepository;

    public List<Watchlist> getAllWatchlists() {
        return watchlistRepository.findAll();
    }

    public Optional<Watchlist> getWatchlistById(Long id) {
        return watchlistRepository.findById(id);
    }

    public List<Watchlist> getWatchlistsByUserId(Long userId) {
        return watchlistRepository.findByUserId(userId);
    }

    public List<Watchlist> getWatchlistsByUsername(String username) {
        return watchlistRepository.findByUserUsername(username);
    }

    public Watchlist createWatchlist(Watchlist watchlist, Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        
        if (watchlistRepository.existsByNameAndUserId(watchlist.getName(), userId)) {
            throw new RuntimeException("Watchlist with name '" + watchlist.getName() + "' already exists for this user");
        }
        
        watchlist.setUser(user);
        return watchlistRepository.save(watchlist);
    }

    public Watchlist updateWatchlist(Long id, Watchlist watchlistDetails) {
        Watchlist watchlist = watchlistRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Watchlist not found with id: " + id));
        
        watchlist.setName(watchlistDetails.getName());
        watchlist.setRefreshRate(watchlistDetails.getRefreshRate());
        return watchlistRepository.save(watchlist);
    }

    public void deleteWatchlist(Long id) {
        Watchlist watchlist = watchlistRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Watchlist not found with id: " + id));
        watchlistRepository.delete(watchlist);
    }

    public Watchlist addCardPrintingToWatchlist(Long watchlistId, Long cardPrintingId) {
        Watchlist watchlist = watchlistRepository.findById(watchlistId)
            .orElseThrow(() -> new RuntimeException("Watchlist not found with id: " + watchlistId));
        
        CardPrinting cardPrinting = cardPrintingRepository.findById(cardPrintingId)
            .orElseThrow(() -> new RuntimeException("Card printing not found with id: " + cardPrintingId));
        
        if (!watchlist.getCardPrintings().contains(cardPrinting)) {
            watchlist.getCardPrintings().add(cardPrinting);
            return watchlistRepository.save(watchlist);
        }
        
        return watchlist; // Already exists
    }

    public Watchlist removeCardPrintingFromWatchlist(Long watchlistId, Long cardPrintingId) {
        Watchlist watchlist = watchlistRepository.findById(watchlistId)
            .orElseThrow(() -> new RuntimeException("Watchlist not found with id: " + watchlistId));
        
        CardPrinting cardPrinting = cardPrintingRepository.findById(cardPrintingId)
            .orElseThrow(() -> new RuntimeException("Card printing not found with id: " + cardPrintingId));
        
        watchlist.getCardPrintings().remove(cardPrinting);
        return watchlistRepository.save(watchlist);
    }
}