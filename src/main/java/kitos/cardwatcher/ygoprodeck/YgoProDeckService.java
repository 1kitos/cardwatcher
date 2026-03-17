package kitos.cardwatcher.ygoprodeck;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import kitos.cardwatcher.ygoprodeck.dtos.YgoApiResponseDTO;
import kitos.cardwatcher.ygoprodeck.dtos.YgoCardDTO;

import java.util.List;

@Service
public class YgoProDeckService {

    private final RestClient restClient;

    public YgoProDeckService() {
        this.restClient = RestClient.builder()
            .baseUrl("https://db.ygoprodeck.com/api/v7")
            .build();
    }

    public List<YgoCardDTO> searchByName(String name) {
        YgoApiResponseDTO response = restClient.get()
            .uri("/cardinfo.php?fname={name}", name)
            .retrieve()
            .body(YgoApiResponseDTO.class);

        return response != null ? response.getData() : List.of();
    }
}