package br.edu.unifaj.service;

import br.edu.unifaj.domain.User;
import br.edu.unifaj.dto.model.UserDto;
import br.edu.unifaj.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User save(UserDto dto) {
        User newUser = new User();
        newUser.setName(dto.getName());
        newUser.setEmail(dto.getEmail());
        newUser.setPass(dto.getPass());
        newUser.setCreationDate(Instant.now());

        return repository.save(newUser);
    }
}
