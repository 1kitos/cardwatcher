package kitos.cardwatcher.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import kitos.cardwatcher.entities.CardPrice;
import kitos.cardwatcher.entities.CardPrinting;
import kitos.cardwatcher.repositories.CardPriceRepository;
import kitos.cardwatcher.repositories.CardPrintingRepository;
import java.util.List;
import java.util.Optional;

@Service
public class CardPriceService {

    @Autowired
    private CardPriceRepository cardPriceRepository;

    @Autowired
    private CardPrintingRepository cardPrintingRepository;

    // === GET PRICE BY ID ===
    public Optional<CardPrice> getCardPriceById(Long id) {
        return cardPriceRepository.findById(id);
    }

    // === GET PRICES BY CARD PRINTING ID ===
    public List<CardPrice> getPricesByCardPrintingId(Long cardPrintingId) {
        return cardPriceRepository.findByCardPrintingId(cardPrintingId);
    }

    // === GET LATEST PRICES BY CARD PRINTING ID ===
    public List<CardPrice> getLatestPricesByCardPrintingId(Long cardPrintingId) {
        return cardPriceRepository.findByCardPrintingIdOrderByTimestampDesc(cardPrintingId);
    }

    // === GET ALL PRICES ===
    public List<CardPrice> getAllCardPrices() {
        return cardPriceRepository.findAll();
    }

    // === CREATE OR UPDATE PRICE ===
    public CardPrice saveCardPrice(CardPrice cardPrice) {
        return cardPriceRepository.save(cardPrice);
    }

    // === CREATE PRICE FOR SPECIFIC PRINTING ===
    public CardPrice createPriceForPrinting(Long cardPrintingId, CardPrice cardPrice) {
        Optional<CardPrinting> printing = cardPrintingRepository.findById(cardPrintingId);
        if (printing.isPresent()) {
            cardPrice.setCardPrinting(printing.get());
            return cardPriceRepository.save(cardPrice);
        }
        throw new RuntimeException("Card printing not found with id: " + cardPrintingId);
    }

    // === UPDATE PRICE ===
    public CardPrice updateCardPrice(Long id, CardPrice priceDetails) {
        return cardPriceRepository.findById(id)
                .map(price -> {
                    // Update price fields - you'd need to see the actual CardPrice entity fields
                    // price.setPrice(priceDetails.getPrice());
                    // price.setTimestamp(priceDetails.getTimestamp());
                    // price.setSource(priceDetails.getSource());
                    return cardPriceRepository.save(price);
                })
                .orElseThrow(() -> new RuntimeException("Card price not found with id: " + id));
    }

    // === DELETE PRICE ===
    public void deleteCardPrice(Long id) {
        cardPriceRepository.deleteById(id);
    }
}