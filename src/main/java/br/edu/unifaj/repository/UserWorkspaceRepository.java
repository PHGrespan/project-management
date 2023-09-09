package br.edu.unifaj.repository;

import br.edu.unifaj.entity.UserWorkspace;
import br.edu.unifaj.entity.UserWorkspaceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserWorkspaceRepository extends JpaRepository<UserWorkspace, UserWorkspaceId> {
}