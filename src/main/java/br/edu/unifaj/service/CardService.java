package br.edu.unifaj.service;

import br.edu.unifaj.dto.CardDto;
import br.edu.unifaj.entity.Card;
import br.edu.unifaj.entity.Catalog;
import br.edu.unifaj.mapper.CardMapper;
import br.edu.unifaj.repository.CardRepository;
import br.edu.unifaj.repository.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class CardService {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    CatalogRepository catalogRepository;

    public Card insert(CardDto dto) throws Exception {

        Catalog catalog = catalogRepository.findById(dto.getIdCatalog()).orElseThrow(() -> new Exception("Catalog not found"));

        // Sort descending by catalogPosition
        List<Card> cards = catalog.getCards();
        cards.sort(Comparator.comparing(Card::getCatalogPosition).reversed());

        // Update catalogPosition from other Cards
        for (Card card : cards) {
            if (card.getCatalogPosition() >= dto.getCatalogPosition()) {
                card.setCatalogPosition(card.getCatalogPosition() + 1);
                cardRepository.save(card);
            }
        }

        Card newCard = CardMapper.INSTANCE.cardDtoToCard(dto);
        newCard.setCatalog(catalog);

        return cardRepository.save(newCard);
    }

    public Card update(Long id, CardDto dto) throws Exception {
        Card oldCard = cardRepository.findById(id).orElseThrow(() -> new Exception("Card not found"));

        Card newCard = CardMapper.INSTANCE.cardDtoToCard(dto);
        newCard.setId(oldCard.getId());

        return cardRepository.save(newCard);
    }

    public Catalog findCatalogWithCardsByCatalogId(Long catalogId) throws Exception {
        return catalogRepository.findById(catalogId).orElseThrow(() -> new Exception("Catalog not found"));
    }

    public void deleteCardById(Long id) {
        cardRepository.deleteById(id);

    }
}
