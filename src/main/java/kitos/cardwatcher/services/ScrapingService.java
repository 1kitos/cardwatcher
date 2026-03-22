package kitos.cardwatcher.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kitos.cardwatcher.entities.Card;
import kitos.cardwatcher.entities.CardGame;
import kitos.cardwatcher.entities.CardPrice;
import kitos.cardwatcher.entities.CardPrinting;
import kitos.cardwatcher.repositories.CardGameRepository;
import kitos.cardwatcher.repositories.CardPriceRepository;
import kitos.cardwatcher.repositories.CardPrintingRepository;
import kitos.cardwatcher.repositories.CardRepository;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    
    private static final String FLARESOLVERR_URL = "http://localhost:8191/v1";

    // Page Navigation for yugioh cards on cardmarket
    
    private String fetchHtml(String url) throws Exception {
        String body = """
            {
                "cmd": "request.get",
                "url": "%s",
                "maxTimeout": 60000
            }
            """.formatted(url);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(FLARESOLVERR_URL))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.body());
        return root.path("solution").path("response").asText();
    }

    private String extractLabeledValue(Document doc, String label) {
        return doc.select("dt:containsOwn(" + label + ") + dd").text();
    }

    private float parsePrice(String price) {
        return Float.parseFloat(price.replace("€", "").replace(",", ".").trim());
    }

    public String getPageTitle(String url) throws Exception {
        String html = fetchHtml(url);
        return Jsoup.parse(html).title();
    }
    
    public String buildCardmarketUrl(String game, String setName, String cardName) {
        return "https://www.cardmarket.com/en/" + toCardmarketFormat(game) + "/Products/Singles/"
            + toCardmarketFormat(setName) + "/" + toCardmarketFormat(cardName);
    }
    
    private String toCardmarketFormat(String input) {
        return input.replaceAll("[^a-zA-Z0-9 ]", "").trim().replaceAll(" +", "-");
    }
    
    
    // SCRAPING LOGIC
    
    
    public CardPrice scrapeCardPrice(String url) throws Exception {
        String html = fetchHtml(url);
        Document doc = Jsoup.parse(html);

        String trend = extractLabeledValue(doc, "Price Trend");
        String average30 = extractLabeledValue(doc, "30-days average price");
        String low = extractLabeledValue(doc, "From");

        CardPrice cardPrice = new CardPrice();
        cardPrice.setTimestamp(LocalDateTime.now());
        cardPrice.setPriceTrend(parsePrice(trend));
        cardPrice.setPriceAverage(parsePrice(average30));
        cardPrice.setPriceLow(parsePrice(low));

        return cardPrice;
    }
    
    public CardPrinting scrapeCardPrinting(String url, boolean save) throws Exception {
        String html = fetchHtml(url);
        Document doc = Jsoup.parse(html);

        String cardName = doc.selectFirst("h1").ownText().trim();

        // Raridade — tenta o aria-label do SVG primeiro
        String rarity = doc.select("dt:containsOwn(Rarity) + dd svg").attr("aria-label");
        if (rarity.isEmpty()) {
            rarity = doc.select("dt:containsOwn(Rarity) + dd").text().trim();
        }

        String number = doc.select("dt:containsOwn(Number) + dd").text().trim();

        String[] parts = url.split("/");
        String gameName = parts[4];
        String setCode = parts[7];

        String trend = extractLabeledValue(doc, "Price Trend");
        String average30 = extractLabeledValue(doc, "30-days average price");
        String low = extractLabeledValue(doc, "From");

        CardPrice cardPrice = new CardPrice();
        cardPrice.setTimestamp(LocalDateTime.now());
        cardPrice.setPriceTrend(parsePrice(trend));
        cardPrice.setPriceAverage(parsePrice(average30));
        cardPrice.setPriceLow(parsePrice(low));

        if (!save) {
            CardPrinting temp = new CardPrinting();
            temp.setSetCode(setCode);
            temp.setRarity(rarity);
            temp.setSerialNumber(number);
            temp.setPriceHistory(java.util.List.of(cardPrice));
            return temp;
        }

        CardGame cardGame = cardGameRepository.findByNameIgnoreCase(gameName).orElseGet(() -> {
            CardGame newGame = new CardGame();
            newGame.setName(gameName);
            return cardGameRepository.save(newGame);
        });

        Card card = Optional.ofNullable(cardRepository.findByNameAndCardGame(cardName, cardGame)).orElseGet(() -> {
            Card newCard = new Card();
            newCard.setName(cardName);
            newCard.setCardGame(cardGame);
            return cardRepository.save(newCard);
        });

        String finalRarity = rarity;
        String finalNumber = number;
        CardPrinting printing = cardPrintingRepository.findByCardIdAndSetCode(card.getId(), setCode).stream()
            .filter(p -> p.getSerialNumber().equals(finalNumber)).findFirst().orElseGet(() -> {
                CardPrinting newPrinting = new CardPrinting();
                newPrinting.setCard(card);
                newPrinting.setSetCode(setCode);
                newPrinting.setRarity(finalRarity);
                newPrinting.setSerialNumber(finalNumber);
                return cardPrintingRepository.save(newPrinting);
            });

        cardPrice.setCardPrinting(printing);
        cardPriceRepository.save(cardPrice);
        return printing;
    }

    public CardPrinting scrapeByCardInfo(String game, String setName, String cardName, boolean save) throws Exception {
        String url = buildCardmarketUrl(game, setName, cardName);
        return scrapeCardPrinting(url, save);
    }

}