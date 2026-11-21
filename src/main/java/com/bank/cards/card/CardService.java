package com.bank.cards.card;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        if (status == null) {
            return cardMapper.toDtoList(
                    cardRepository.findByClientIdOrderByIdAsc(clientId)
            );
        }
        return cardMapper.toDtoList(
                cardRepository.findByClientIdAndStatusOrderByIdAsc(clientId, status)
        );
    }

    public List<CardDto> getAllCards() {
        return cardMapper.toDtoList(cardRepository.findAllByOrderByCreatedAtDescIdDesc());
    }

    public CardDto createCard(CreateCardRequest request) {
        Card card = Card.builder()
                .clientId(request.getClientId())
                .pan(request.getPan())
                .cardHolderName(request.getCardHolderName())
                .cardType(request.getCardType())
                .paymentSystem(request.getPaymentSystem())
                .validFrom(request.getValidFrom())
                .validTo(request.getValidTo())
                .productName(request.getProductName())
                .status(CardStatus.NEW)
                .createdAt(LocalDateTime.now())
                .build();

        Card saved = cardRepository.save(card);
        return cardMapper.toDto(saved);
    }

    public CardDto updateCard(long id, UpdateCardRequest request) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException(id));

        card.setProductName(request.getProductName());
        card.setValidTo(request.getValidTo());

        return cardMapper.toDto(card);
    }

    public void closeCard(long id) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException(id));
        if (card.getStatus() == CardStatus.CLOSED) {
            throw new CardOperationException("Карта " + id + " уже закрыта");
        }
        card.setStatus(CardStatus.CLOSED);
        cardRepository.save(card);
    }
}
