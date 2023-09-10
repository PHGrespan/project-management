package br.edu.unifaj.repository;

import br.edu.unifaj.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    @Modifying
    @Query(value = "UPDATE card c SET c.catalog_position = c.catalog_position - :decrement where c.catalog_id = :catalogId AND c.catalog_position BETWEEN :catalogPositionInitial AND :catalogPositionFinal ORDER BY catalog_position", nativeQuery = true)
    void updateAllCardsDecrementCatalogPositionByCatalogIdAndCatalogPositionBetween(@Param("catalogId") Long catalogId,
                                                                                    @Param("catalogPositionInitial") Integer catalogPositionInitial,
                                                                                    @Param("catalogPositionFinal") Integer catalogPositionFinal,
                                                                                    @Param("decrement") Integer decrement);
    @Modifying
    @Query(value = "UPDATE card c SET c.catalog_position = c.catalog_position + :increment where c.catalog_id = :catalogId AND c.catalog_position BETWEEN :catalogPositionInitial AND :catalogPositionFinal ORDER BY catalog_position DESC", nativeQuery = true)
    void updateAllCardsIncrementCatalogPositionByCatalogIdAndCatalogPositionBetween(@Param("catalogId") Long catalogId,
                                                                                    @Param("catalogPositionInitial") Integer catalogPositionInitial,
                                                                                    @Param("catalogPositionFinal") Integer catalogPositionFinal,
                                                                                    @Param("increment") Integer increment);

    @Modifying
    @Query(value = "SELECT MAX(catalog_position) FROM card WHERE catalog_id = :catalogId", nativeQuery = true)
    List<Integer> findMaxCatalogPosition(@Param("catalogId") Long catalogId);
}