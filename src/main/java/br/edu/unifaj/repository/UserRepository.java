package br.edu.unifaj.repository;

import br.edu.unifaj.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}