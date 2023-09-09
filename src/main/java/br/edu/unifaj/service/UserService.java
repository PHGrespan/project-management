package br.edu.unifaj.service;

import br.edu.unifaj.entity.User;
import br.edu.unifaj.dto.UserDto;
import br.edu.unifaj.mapper.UserMapper;
import br.edu.unifaj.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User save(UserDto dto) {
        User newUser = UserMapper.INSTANCE.UserDtoToUser(dto);

        newUser.setCreationDate(LocalDateTime.now());

        return userRepository.save(newUser);
    }

    public User update(Long id, UserDto dto) throws Exception {
        User oldUser = userRepository.findById(id).orElseThrow(() -> new Exception("User not found"));

        User newUser = UserMapper.INSTANCE.UserDtoToUser(dto);
        newUser.setId(oldUser.getId());
        newUser.setCreationDate(oldUser.getCreationDate());

        return userRepository.save(newUser);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
