package br.edu.unifaj.controller;

import br.edu.unifaj.dto.CardDto;
import br.edu.unifaj.entity.Card;
import br.edu.unifaj.entity.Catalog;
import br.edu.unifaj.service.CardService;
import br.edu.unifaj.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CardController {

    @Autowired
    CardService cardService;

    @JsonView(View.Card.class)
    @GetMapping("/catalogs/{catalogId}/cards")
    public ResponseEntity<Catalog> findAllCardsByCatalogId(@PathVariable(value = "catalogId") Long catalogId) throws Exception {
        return new ResponseEntity<>(cardService.findAllCardsByCatalogId(catalogId), HttpStatus.OK);
    }

    @JsonView(View.Card.class)
    @PostMapping("/cards")
    public ResponseEntity<Card> insertCard(@Valid @RequestBody CardDto dto) throws Exception {
        return new ResponseEntity<>(cardService.save(dto), HttpStatus.CREATED);
    }
    @JsonView(View.Card.class)
    @PutMapping("/cards/{id}")
    public ResponseEntity<Card> updateCard(@PathVariable(value = "id") Long id, @Valid @RequestBody CardDto dto) throws Exception {
        return new ResponseEntity<>(cardService.update(id, dto), HttpStatus.OK);
    }

}
