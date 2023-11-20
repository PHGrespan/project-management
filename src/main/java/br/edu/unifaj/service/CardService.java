package br.edu.unifaj.service;

import br.edu.unifaj.dto.CardDto;
import br.edu.unifaj.entity.Card;
import br.edu.unifaj.entity.Catalog;
import br.edu.unifaj.mapper.CardMapper;
import br.edu.unifaj.repository.CardRepository;
import br.edu.unifaj.repository.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CardService {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    CatalogRepository catalogRepository;

    public Card insert(CardDto dto) throws Exception {
        Card newCard = CardMapper.INSTANCE.cardDtoToCard(dto);
        Catalog catalog = catalogRepository.findById(dto.getIdCatalog()).orElseThrow(() -> new Exception("Catalog not found"));

        if (newCard.getCatalogPosition() == -1) {
            newCard.setCatalogPosition(catalog.getCards().size() + 1);
        } else if (newCard.getCatalogPosition() > catalog.getCards().size() + 1) {
            throw new Exception("Card must be in catalogPosition between " + 1 + " and " + (catalog.getCards().size() + 1));
        }

        // Increments 1 in the catalogPosition of the Cards that should be after the new Card
        cardRepository.updateAllCardsIncrementCatalogPositionByCatalogIdAndCatalogPositionBetween(catalog.getId(), newCard.getCatalogPosition(), cardRepository.findMaxCatalogPositionByCatalogId(catalog.getId()).get(0), 1);

        newCard.setCatalog(catalog);

        return cardRepository.save(newCard);
    }

    public Card update(Long id, CardDto dto) throws Exception {
        Catalog catalog = catalogRepository.findById(dto.getIdCatalog()).orElseThrow(() -> new Exception("Catalog not found"));
        Card oldCard = cardRepository.findById(id).orElseThrow(() -> new Exception("Card not found"));

        Card newCard = CardMapper.INSTANCE.cardDtoToCard(dto);

        // Validations
        // Same Catalog
        if ((newCard.getCatalog().getId().equals(oldCard.getCatalog().getId()) && newCard.getCatalogPosition() > catalog.getCards().size())) {
            throw new Exception("Card must be in catalogPosition between " + 1 + " and " + (catalog.getCards().size()));

            // Different Catalog
        } else if (newCard.getCatalogPosition() > catalog.getCards().size() + 1) {
            throw new Exception("Card must be in catalogPosition between " + 1 + " and " + (catalog.getCards().size() + 1));
        }

        newCard.setId(oldCard.getId());
        Integer oldCatalogPosition = oldCard.getCatalogPosition();
        Integer newCatalogPosition = newCard.getCatalogPosition();

        // Move Card to catalogPosition 0
        oldCard.setCatalogPosition(0);
        cardRepository.save(oldCard);

        // Same Catalog
        if (newCard.getCatalog().getId().equals(oldCard.getCatalog().getId())) {

            // Change catalogPosition
            if (!newCatalogPosition.equals(oldCatalogPosition)) {

                // new catalogPosition > old catalogPosition
                if (newCatalogPosition > oldCatalogPosition) {
                    // Decreases 1 in the catalogPosition of the Cards that should be after the old Card and before the new Card
                    cardRepository.updateAllCardsDecrementCatalogPositionByCatalogIdAndCatalogPositionBetween(newCard.getCatalog().getId(), oldCatalogPosition + 1, newCatalogPosition, 1);

                    // new catalogPosition < old catalogPosition
                } else {
                    // Increments 1 in the catalogPosition of the Cards that should be after the old Card and before the new Card
                    cardRepository.updateAllCardsIncrementCatalogPositionByCatalogIdAndCatalogPositionBetween(newCard.getCatalog().getId(), newCatalogPosition, oldCatalogPosition - 1, 1);
                }
            }

            // Different Catalog
        } else {
            // Decreases 1 in the catalogPosition of Cards that have catalogPosition before the old Card
            cardRepository.updateAllCardsDecrementCatalogPositionByCatalogIdAndCatalogPositionBetween(oldCard.getCatalog().getId(), oldCatalogPosition + 1, cardRepository.findMaxCatalogPositionByCatalogId(oldCard.getCatalog().getId()).get(0), 1);
            // Increments 1 in the catalogPosition of the Cards that should be after the new Card
            cardRepository.updateAllCardsIncrementCatalogPositionByCatalogIdAndCatalogPositionBetween(newCard.getCatalog().getId(), dto.getCatalogPosition(), cardRepository.findMaxCatalogPositionByCatalogId(newCard.getCatalog().getId()).get(0), 1);
        }

        return cardRepository.save(newCard);
    }

    public void deleteCardById(Long id) throws Exception {
        Card card = cardRepository.findById(id).orElseThrow(() -> new Exception("Catalog not found"));
        cardRepository.deleteById(id);
        // Decreases 1 in the catalogPosition of Cards that were before the deleted Card
        cardRepository.updateAllCardsDecrementCatalogPositionByCatalogIdAndCatalogPositionBetween(card.getCatalog().getId(), card.getCatalogPosition() + 1, cardRepository.findMaxCatalogPositionByCatalogId(card.getCatalog().getId()).get(0), 1);
    }
}
