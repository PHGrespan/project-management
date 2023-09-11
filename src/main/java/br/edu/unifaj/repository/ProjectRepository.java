package br.edu.unifaj.repository;

import br.edu.unifaj.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Modifying
    @Query(value = "UPDATE project c SET c.workspace_position = c.workspace_position - :decrement where c.workspace_id = :workspaceId AND c.workspace_position BETWEEN :workspacePositionInitial AND :workspacePositionFinal ORDER BY workspace_position", nativeQuery = true)
    void updateAllProjectsDecrementWorkspacePositionByWorkspaceIdAndWorkspacePositionBetween(@Param("workspaceId") Long workspaceId,
                                                                                      @Param("workspacePositionInitial") Integer workspacePositionInitial,
                                                                                      @Param("workspacePositionFinal") Integer workspacePositionFinal,
                                                                                      @Param("decrement") Integer decrement);

    @Modifying
    @Query(value = "UPDATE project c SET c.workspace_position = c.workspace_position + :increment where c.workspace_id = :workspaceId AND c.workspace_position BETWEEN :workspacePositionInitial AND :workspacePositionFinal ORDER BY workspace_position DESC", nativeQuery = true)
    void updateAllProjectsIncrementWorkspacePositionByWorkspaceIdAndWorkspacePositionBetween(@Param("workspaceId") Long workspaceId,
                                                                                      @Param("workspacePositionInitial") Integer workspacePositionInitial,
                                                                                      @Param("workspacePositionFinal") Integer workspacePositionFinal,
                                                                                      @Param("increment") Integer increment);

    @Query(value = "SELECT MAX(workspace_position) FROM project WHERE workspace_id = :workspaceId", nativeQuery = true)
    List<Integer> findMaxWorkspacePositionByWorkspaceId(@Param("workspaceId") Long workspaceId);
}