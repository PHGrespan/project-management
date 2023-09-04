package br.edu.unifaj.repository;

import br.edu.unifaj.domain.UserWorkspace;
import br.edu.unifaj.domain.UserWorkspaceId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWorkspaceRepository extends JpaRepository<UserWorkspace, UserWorkspaceId> {
}