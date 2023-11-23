package br.edu.unifaj.repository;

import br.edu.unifaj.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailAndPass(String email, String pass);
    Optional<User> findByEmail(String email);
}