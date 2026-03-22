package kitos.cardwatcher.controllers.rest;

import kitos.cardwatcher.dtos.shared.CardPrintingDTO;
import kitos.cardwatcher.entities.CardPrice;
import kitos.cardwatcher.services.ScrapingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/scraping")
public class ScrapingController {

    private final ScrapingService scrapingService;

    public ScrapingController(ScrapingService scrapingService) {
        this.scrapingService = scrapingService;
    }

    @GetMapping("/title")
    public String getPageTitle(@RequestParam("url") String url) {
        try {
            return scrapingService.getPageTitle(url);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get title: " + e.getMessage());
        }
    }

//    @GetMapping("/price")
//    public CardPrice getCardPrice(@RequestParam("url") String url) {
//        try {
//            return scrapingService.scrapeCardPrice(url);
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to scrape price: " + e.getMessage());
//        }
//    }

    @GetMapping("/scrape")
    public CardPrintingDTO scrapeCardPrinting(
            @RequestParam("url") String url,
            @RequestParam(value = "save", defaultValue = "true") boolean save) {
        try {
            return new CardPrintingDTO(scrapingService.scrapeCardPrinting(url, save));
        } catch (Exception e) {
            throw new RuntimeException("Scraping failed: " + e.getMessage());
        }
    }

    @GetMapping("/scrape-by-name")
    public CardPrintingDTO scrapeByName(
            @RequestParam("game") String game,
            @RequestParam("set") String set,
            @RequestParam("card") String card,
            @RequestParam(value = "save", defaultValue = "true") boolean save) {
        try {
        	String url = scrapingService.buildCardmarketUrl(game, set, card);
            return new CardPrintingDTO(scrapingService.scrapeCardPrinting(url, save));
        } catch (Exception e) {
            throw new RuntimeException("Scraping failed: " + e.getMessage());
        }
    }
}