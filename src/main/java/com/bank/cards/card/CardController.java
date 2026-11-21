package com.bank.cards.card;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/api/cards/{id}")
    public CardDto getCard(@PathVariable long id) {
        return cardService.getCard(id);
    }

    @GetMapping("/api/clients/{clientId}/cards")
    public List<CardDto> getClientCards(@PathVariable long clientId) {
        return cardService.getCardsByClient(clientId);
    }
}
