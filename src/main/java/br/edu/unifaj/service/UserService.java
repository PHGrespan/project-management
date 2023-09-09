package br.edu.unifaj.service;

import br.edu.unifaj.entity.User;
import br.edu.unifaj.dto.UserDto;
import br.edu.unifaj.entity.UserWorkspace;
import br.edu.unifaj.mapper.UserMapper;
import br.edu.unifaj.repository.UserRepository;
import br.edu.unifaj.repository.UserWorkspaceRepository;
import br.edu.unifaj.repository.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserWorkspaceRepository userWorkspaceRepository;

    @Autowired
    private WorkspaceRepository workspaceRepository;

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
        List<UserWorkspace> userWorkspaces = userWorkspaceRepository.findAllByUserIdAndOwner(id, true);

        for (UserWorkspace userWorkspace : userWorkspaces) {
           workspaceRepository.deleteById(userWorkspace.getWorkspace().getId());
        }

        userRepository.deleteById(id);
    }
}
