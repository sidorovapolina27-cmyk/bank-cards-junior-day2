package com.bank.cards.card;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public List<CardDto> getClientCards(@PathVariable long clientId,
                                        @RequestParam(required = false) CardStatus status) {
        return cardService.getCardsByClient(clientId, status);
    }

    @GetMapping("/api/cards")
    public List<CardDto> getAllCards() {
        return cardService.getAllCards();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/cards")
    public CardDto createCard(@RequestBody CreateCardRequest request) {
        return cardService.createCard(request);
    }

    @PutMapping("/api/cards/{id}")
    public CardDto updateCard(@PathVariable long id, @RequestBody UpdateCardRequest request) {
        return cardService.updateCard(id, request);
    }

    @DeleteMapping("/api/cards/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void closeCard(@PathVariable long id) {
        cardService.closeCard(id);
    }
}
