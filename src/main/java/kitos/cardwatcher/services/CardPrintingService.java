package kitos.cardwatcher.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import kitos.cardwatcher.entities.CardPrice;
import kitos.cardwatcher.entities.CardPrinting;
import kitos.cardwatcher.repositories.CardPrintingRepository;
import java.util.List;
import java.util.Optional;

@Service
public class CardPrintingService {

    @Autowired
    private CardPrintingRepository cardPrintingRepository;

    // === GET CARD PRINTING BY ID ===
    public Optional<CardPrinting> getCardPrintingById(Long id) {
        return cardPrintingRepository.findById(id);
    }

    // === GET PRINTINGS BY CARD ID ===
    public List<CardPrinting> getPrintingsByCardId(Long cardId) {
        return cardPrintingRepository.findByCardId(cardId);
    }

    // === GET PRINTINGS BY SET CODE ===
    public List<CardPrinting> getPrintingsBySetCode(String setCode) {
        return cardPrintingRepository.findBySetCode(setCode);
    }

    // === GET PRINTINGS BY RARITY ===
    public List<CardPrinting> getPrintingsByRarity(String rarity) {
        return cardPrintingRepository.findByRarity(rarity);
    }

    // === GET PRINTING BY SERIAL NUMBER ===
    public Optional<CardPrinting> getPrintingBySerialNumber(String serialNumber) {
        return cardPrintingRepository.findBySerialNumber(serialNumber);
    }

    // === GET ALL CARD PRINTINGS ===
    public List<CardPrinting> getAllCardPrintings() {
        return cardPrintingRepository.findAll();
    }

    // === CREATE OR UPDATE CARD PRINTING ===
    public CardPrinting saveCardPrinting(CardPrinting cardPrinting) {
        return cardPrintingRepository.save(cardPrinting);
    }

    // === UPDATE CARD PRINTING ===
    public CardPrinting updateCardPrinting(Long id, CardPrinting cardPrintingDetails) {
        return cardPrintingRepository.findById(id)
                .map(printing -> {
                    printing.setSetCode(cardPrintingDetails.getSetCode());
                    printing.setRarity(cardPrintingDetails.getRarity());
                    printing.setSerialNumber(cardPrintingDetails.getSerialNumber());
                    printing.setCard(cardPrintingDetails.getCard());
                    return cardPrintingRepository.save(printing);
                })
                .orElseThrow(() -> new RuntimeException("CardPrinting not found with id: " + id));
    }

    // === DELETE CARD PRINTING ===
    public void deleteCardPrinting(Long id) {
        cardPrintingRepository.deleteById(id);
    }
    
    
    public List<CardPrice> getPriceHistory(Long printingId) {
		return null;
        // Would fetch CardPrice records for this printing
    }

    public CardPrinting addPriceToPrinting(Long printingId, CardPrice cardPrice) {
		return null;
        // Would associate a price with this printing
    }
    
    
    
}