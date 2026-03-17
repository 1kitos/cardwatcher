package kitos.cardwatcher.ygoprodeck;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import kitos.cardwatcher.ygoprodeck.dtos.YgoCardDTO;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class YgoProDeckController {

    @Autowired
    private YgoProDeckService ygoProDeckService;

    @GetMapping("/search")
    public List<YgoCardDTO> searchCards(@RequestParam("name") String name) {
        return ygoProDeckService.searchByName(name);
    }
}
