// kitos.cardwatcher.controllers.rest.WatchlistController.java
package kitos.cardwatcher.controllers.rest;

import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import kitos.cardwatcher.dtos.requests.CreateWatchlistRequest;
import kitos.cardwatcher.dtos.requests.UpdateWatchlistRequest;
import kitos.cardwatcher.dtos.responses.WatchlistResponse;
import kitos.cardwatcher.dtos.shared.WatchlistDTO;
import kitos.cardwatcher.entities.Watchlist;
import kitos.cardwatcher.services.WatchlistService;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/watchlists")
@Tag(name = "Watchlists", description = "Watchlist management operations")
public class WatchlistController {

    @Autowired
    private WatchlistService watchlistService;

    @GetMapping
    public List<WatchlistDTO> getAllWatchlists() {
        return watchlistService.getAllWatchlists().stream()
            .map(WatchlistDTO::new)
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WatchlistResponse> getWatchlistById(@PathVariable Long id) {
        return watchlistService.getWatchlistById(id)
            .map(watchlist -> ResponseEntity.ok(new WatchlistResponse(watchlist)))
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public List<WatchlistDTO> getWatchlistsByUserId(@PathVariable Long userId) {
        return watchlistService.getWatchlistsByUserId(userId).stream()
            .map(WatchlistDTO::new)
            .collect(Collectors.toList());
    }

    @GetMapping("/user/username/{username}")
    public List<WatchlistDTO> getWatchlistsByUsername(@PathVariable String username) {
        return watchlistService.getWatchlistsByUsername(username).stream()
            .map(WatchlistDTO::new)
            .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<WatchlistResponse> createWatchlist(@RequestBody CreateWatchlistRequest createWatchlistRequest) {
        Watchlist watchlist = new Watchlist();
        watchlist.setName(createWatchlistRequest.getName());
        watchlist.setRefreshRate(createWatchlistRequest.getRefreshRate());
        
        Watchlist savedWatchlist = watchlistService.createWatchlist(watchlist, createWatchlistRequest.getUserId());
        WatchlistResponse response = new WatchlistResponse(savedWatchlist);
        
        return ResponseEntity
            .created(URI.create("/api/watchlists/" + savedWatchlist.getId()))
            .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WatchlistResponse> updateWatchlist(
            @PathVariable Long id,
            @RequestBody UpdateWatchlistRequest updateWatchlistRequest) {
        try {
            Watchlist watchlist = new Watchlist();
            watchlist.setName(updateWatchlistRequest.getName());
            watchlist.setRefreshRate(updateWatchlistRequest.getRefreshRate());
            
            Watchlist updatedWatchlist = watchlistService.updateWatchlist(id, watchlist);
            WatchlistResponse response = new WatchlistResponse(updatedWatchlist);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWatchlist(@PathVariable Long id) {
        try {
            watchlistService.deleteWatchlist(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{watchlistId}/card-printings/{cardPrintingId}")
    public ResponseEntity<WatchlistResponse> addCardPrintingToWatchlist(
            @PathVariable Long watchlistId,
            @PathVariable Long cardPrintingId) {
        try {
            Watchlist updatedWatchlist = watchlistService.addCardPrintingToWatchlist(watchlistId, cardPrintingId);
            WatchlistResponse response = new WatchlistResponse(updatedWatchlist);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{watchlistId}/card-printings/{cardPrintingId}")
    public ResponseEntity<WatchlistResponse> removeCardPrintingFromWatchlist(
            @PathVariable Long watchlistId,
            @PathVariable Long cardPrintingId) {
        try {
            Watchlist updatedWatchlist = watchlistService.removeCardPrintingFromWatchlist(watchlistId, cardPrintingId);
            WatchlistResponse response = new WatchlistResponse(updatedWatchlist);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}