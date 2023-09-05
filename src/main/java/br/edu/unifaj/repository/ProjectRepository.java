package br.edu.unifaj.repository;

import br.edu.unifaj.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}