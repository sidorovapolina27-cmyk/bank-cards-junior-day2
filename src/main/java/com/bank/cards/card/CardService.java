package com.bank.cards.card;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private final CardMapper cardMapper;

    public CardService(CardRepository cardRepository, CardMapper cardMapper) {
        this.cardRepository = cardRepository;
        this.cardMapper = cardMapper;
    }

    public CardDto getCard(long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException(id));
        return cardMapper.toDto(card);
    }

    public List<CardDto> getCardsByClient(long clientId) {
        return cardMapper.toDtoList(cardRepository.findByClientIdOrderByIdAsc(clientId));
    }

    public List<CardDto> getCardsByClient(long clientId, CardStatus status) {
        throw new UnsupportedOperationException("Не реализовано: задание 2.3");
    }

    public List<CardDto> getAllCards() {
        throw new UnsupportedOperationException("Не реализовано: задание 2.5");
    }

    public CardDto createCard(CreateCardRequest request) {
        throw new UnsupportedOperationException("Не реализовано: задание 2.1");
    }

    public CardDto updateCard(long id, UpdateCardRequest request) {
        throw new UnsupportedOperationException("Не реализовано: задание 2.2");
    }

    public void closeCard(long id) {
        throw new UnsupportedOperationException("Не реализовано: задание 2.4");
    }
}
