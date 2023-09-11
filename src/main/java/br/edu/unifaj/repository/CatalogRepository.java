package br.edu.unifaj.repository;

import br.edu.unifaj.entity.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog, Long> {

    @Modifying
    @Query(value = "UPDATE catalog c SET c.project_position = c.project_position - :decrement where c.project_id = :projectId AND c.project_position BETWEEN :projectPositionInitial AND :projectPositionFinal ORDER BY project_position", nativeQuery = true)
    void updateAllCatalogsDecrementProjectPositionByProjectIdAndProjectPositionBetween(@Param("projectId") Long projectId,
                                                                                       @Param("projectPositionInitial") Integer projectPositionInitial,
                                                                                       @Param("projectPositionFinal") Integer projectPositionFinal,
                                                                                       @Param("decrement") Integer decrement);

    @Modifying
    @Query(value = "UPDATE catalog c SET c.project_position = c.project_position + :increment where c.project_id = :projectId AND c.project_position BETWEEN :projectPositionInitial AND :projectPositionFinal ORDER BY project_position DESC", nativeQuery = true)
    void updateAllCatalogsIncrementProjectPositionByProjectIdAndProjectPositionBetween(@Param("projectId") Long projectId,
                                                                                       @Param("projectPositionInitial") Integer projectPositionInitial,
                                                                                       @Param("projectPositionFinal") Integer projectPositionFinal,
                                                                                       @Param("increment") Integer increment);

    @Query(value = "SELECT MAX(project_position) FROM catalog WHERE project_id = :projectId", nativeQuery = true)
    List<Integer> findMaxProjectPosition(@Param("projectId") Long projectId);
}