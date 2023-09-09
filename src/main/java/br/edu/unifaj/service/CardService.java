package br.edu.unifaj.service;

import br.edu.unifaj.dto.CardDto;
import br.edu.unifaj.entity.Card;
import br.edu.unifaj.entity.Catalog;
import br.edu.unifaj.mapper.CardMapper;
import br.edu.unifaj.repository.CardRepository;
import br.edu.unifaj.repository.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    CatalogRepository catalogRepository;

    public Card save(CardDto dto) throws Exception {
        Card newCard = CardMapper.INSTANCE.cardDtoToCard(dto);

        Catalog catalog = catalogRepository.findById(newCard.getCatalog().getId()).orElseThrow(() -> new Exception("Catalog not found"));

        newCard.setCatalog(catalog);

        return cardRepository.save(newCard);
    }

    public Card update(Long id, CardDto dto) throws Exception {
        Card oldCard = cardRepository.findById(id).orElseThrow(() -> new Exception("Card not found"));

        Card newCard = CardMapper.INSTANCE.cardDtoToCard(dto);
        newCard.setId(oldCard.getId());

        return cardRepository.save(newCard);
    }

    public Catalog findAllCardsByCatalogId(Long catalogId) throws Exception {
        return catalogRepository.findById(catalogId).orElseThrow(() -> new Exception("Catalog not found"));
    }

    public void deleteCardById(Long id) {
        cardRepository.deleteById(id);

    }
}
