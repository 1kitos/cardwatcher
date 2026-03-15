package kitos.cardwatcher.controllers.rest;

import kitos.cardwatcher.entities.CardPrice;
import kitos.cardwatcher.entities.CardPrinting;
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
        return scrapingService.getPageTitle(url);
    }
    
    @GetMapping("/price")
    public CardPrice getCardPrice(@RequestParam("url") String url) {
        return scrapingService.scrapeCardPrice(url);
    }
    
    @GetMapping("/scrape")
    public CardPrinting scrapeCardPrinting(@RequestParam("url") String url) {
        return scrapingService.scrapeCardPrinting(url);
    }
}