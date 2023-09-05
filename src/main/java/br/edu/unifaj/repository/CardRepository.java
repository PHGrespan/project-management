package br.edu.unifaj.repository;

import br.edu.unifaj.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}