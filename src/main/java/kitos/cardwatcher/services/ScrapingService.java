package kitos.cardwatcher.services;

import com.microsoft.playwright.*;

import kitos.cardwatcher.entities.Card;
import kitos.cardwatcher.entities.CardGame;
import kitos.cardwatcher.entities.CardPrice;
import kitos.cardwatcher.entities.CardPrinting;
import kitos.cardwatcher.repositories.CardGameRepository;
import kitos.cardwatcher.repositories.CardPriceRepository;
import kitos.cardwatcher.repositories.CardPrintingRepository;
import kitos.cardwatcher.repositories.CardRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScrapingService {
	
	@Autowired
    private CardGameRepository cardGameRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CardPrintingRepository cardPrintingRepository;

    @Autowired
    private CardPriceRepository cardPriceRepository;
	

	private Browser createBrowser(Playwright playwright) {
	    return playwright.chromium().launch(
	        new BrowserType.LaunchOptions()
	            .setHeadless(true)
	            .setExecutablePath(java.nio.file.Paths.get("C:/Program Files/Google/Chrome/Application/chrome.exe"))
	            .setArgs(java.util.List.of(
	                "--disable-blink-features=AutomationControlled",
	                "--no-sandbox",
	                "--disable-dev-shm-usage"
	            ))
	    );
	}

	private Page createPage(Browser browser) {
	    BrowserContext context = browser.newContext(
	        new Browser.NewContextOptions()
	            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
	    );
	    Page page = context.newPage();
	    // Esconde que é automation
	    page.addInitScript("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");
	    return page;
	}

    private Page navigateTo(Browser browser, String url) {
        Page page = createPage(browser);
        page.navigate(url);
        page.waitForFunction("() => document.title !== 'Just a moment...'");
        return page;
    }

    public String getPageTitle(String url) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = createBrowser(playwright);
            Page page = navigateTo(browser, url);
            return page.title();
        }
    }
    
    
    
    public CardPrice scrapeCardPrice(String url) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = createBrowser(playwright);
            Page page = navigateTo(browser, url);

            String trend = extractLabeledValue(page, "Price Trend");
            String average30 = extractLabeledValue(page, "30-days average price");
            String low = extractLabeledValue(page, "From");

            CardPrice cardPrice = new CardPrice();
            cardPrice.setTimestamp(LocalDateTime.now());
            cardPrice.setPriceTrend(parsePrice(trend));
            cardPrice.setPriceAverage(parsePrice(average30));
            cardPrice.setPriceLow(parsePrice(low));

            return cardPrice;
        }
    }

    private String extractLabeledValue(Page page, String label) {
        return (String) page.evalOnSelector(
            "dt:has-text('" + label + "') + dd",
            "el => el.innerText"
        );
    }

    private float parsePrice(String price) {
        return Float.parseFloat(price.replace("€", "").replace(",", ".").trim());
    }
    
    
    public CardPrinting scrapeCardPrinting(String url) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = createBrowser(playwright);
            Page page = navigateTo(browser, url);

            // Extrair da página
            String cardName = (String) page.evalOnSelector("h1", "el => el.firstChild.textContent.trim()");
            String rarity = page.getAttribute("dt:has-text('Rarity') + dd svg", "aria-label");
            String number = (String) page.evalOnSelector("dt:has-text('Number') + dd", "el => el.innerText.trim()");

            // Extrair da URL
            String[] parts = url.split("/");
            String gameName = parts[4];  // YuGiOh
            String setCode = parts[7];   // Duelists-Advance

            // Find or create CardGame
            CardGame cardGame = cardGameRepository.findByNameIgnoreCase(gameName)
                .orElseGet(() -> {
                    CardGame newGame = new CardGame();
                    newGame.setName(gameName);
                    return cardGameRepository.save(newGame);
                });

            // Find or create Card
            Card card = Optional.ofNullable(cardRepository.findByNameAndCardGame(cardName, cardGame))
                .orElseGet(() -> {
                    Card newCard = new Card();
                    newCard.setName(cardName);
                    newCard.setCardGame(cardGame);
                    return cardRepository.save(newCard);
                });

            // Find or create CardPrinting
            CardPrinting printing = cardPrintingRepository
                .findByCardIdAndSetCode(card.getId(), setCode)
                .stream()
                .filter(p -> p.getSerialNumber().equals(number))
                .findFirst()
                .orElseGet(() -> {
                    CardPrinting newPrinting = new CardPrinting();
                    newPrinting.setCard(card);
                    newPrinting.setSetCode(setCode);
                    newPrinting.setRarity(rarity);
                    newPrinting.setSerialNumber(number);
                    return cardPrintingRepository.save(newPrinting);
                });

            // Scrape e guardar o preço
            String trend = extractLabeledValue(page, "Price Trend");
            String average30 = extractLabeledValue(page, "30-days average price");
            String low = extractLabeledValue(page, "From");

            CardPrice cardPrice = new CardPrice();
            cardPrice.setTimestamp(LocalDateTime.now());
            cardPrice.setPriceTrend(parsePrice(trend));
            cardPrice.setPriceAverage(parsePrice(average30));
            cardPrice.setPriceLow(parsePrice(low));
            cardPrice.setCardPrinting(printing);
            cardPriceRepository.save(cardPrice);

            return printing;
        }
    
    
    }
    
    
 // ScrapingService
    public String buildCardmarketUrl(String game, String setName, String cardName) {
        return "https://www.cardmarket.com/en/"
            + toCardmarketFormat(game) + "/Products/Singles/"
            + toCardmarketFormat(setName) + "/"
            + toCardmarketFormat(cardName);
    }

    private String toCardmarketFormat(String input) {
        return input
            .replaceAll("[^a-zA-Z0-9 ]", "")
            .trim()
            .replaceAll(" +", "-");
    }

    public CardPrinting scrapeByCardInfo(String game, String setName, String cardName) {
        String url = buildCardmarketUrl(game, setName, cardName);
        return scrapeCardPrinting(url);
    }
    
    
    
}