package br.edu.unifaj.service;

import br.edu.unifaj.domain.User;
import br.edu.unifaj.dto.model.UserDto;
import br.edu.unifaj.mapper.UserMapper;
import br.edu.unifaj.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User save(UserDto dto) {
        User newUser = UserMapper.INSTANCE.UserDtoToUser(dto);

        newUser.setCreationDate(LocalDateTime.now());

        return repository.save(newUser);
    }

    public User update(Long id, UserDto dto) throws Exception {
        User oldUser = repository.findById(id).orElseThrow(() -> new Exception("User not found"));

        User newUser = UserMapper.INSTANCE.UserDtoToUser(dto);
        newUser.setId(oldUser.getId());
        newUser.setCreationDate(oldUser.getCreationDate());

        return repository.save(newUser);
    }
}
