package br.edu.unifaj.service;

import br.edu.unifaj.dto.WorkspaceDto;
import br.edu.unifaj.entity.User;
import br.edu.unifaj.entity.UserWorkspace;
import br.edu.unifaj.entity.UserWorkspaceId;
import br.edu.unifaj.entity.Workspace;
import br.edu.unifaj.mapper.WorkspaceMapper;
import br.edu.unifaj.repository.UserRepository;
import br.edu.unifaj.repository.UserWorkspaceRepository;
import br.edu.unifaj.repository.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class WorkspaceService {

    @Autowired
    WorkspaceRepository workspaceRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserWorkspaceRepository userWorkspaceRepository;

    public Workspace findWorkspaceWithProjectsByWorkspaceId(Long workspaceId) throws Exception {
        return workspaceRepository.findById(workspaceId).orElseThrow(() -> new Exception("Workspace not found"));
    }

    public Workspace save(Long userId, WorkspaceDto dto) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));

        Workspace newWorkspace= WorkspaceMapper.INSTANCE.WorkspaceDtoToWorkspace(dto);

        newWorkspace.setCreationDate(LocalDateTime.now());
        newWorkspace.setUpdateDate(LocalDateTime.now());

        newWorkspace = workspaceRepository.save(newWorkspace);


        UserWorkspaceId userWorkspaceId = new UserWorkspaceId();
        userWorkspaceId.setUserId(user.getId());
        userWorkspaceId.setWorkspaceId(newWorkspace.getId());

        UserWorkspace newUserWorkspace = new UserWorkspace();
        newUserWorkspace.setId(userWorkspaceId);
        newUserWorkspace.setUser(user);
        newUserWorkspace.setWorkspace(newWorkspace);
        newUserWorkspace.setOwner(true);
        userWorkspaceRepository.save(newUserWorkspace);

        return newWorkspace;
    }

    public Workspace update(Long id, WorkspaceDto dto) throws Exception {
        Workspace oldWorkspace = workspaceRepository.findById(id).orElseThrow(() -> new Exception("Workspace not found"));

        Workspace newWorkspace = WorkspaceMapper.INSTANCE.WorkspaceDtoToWorkspace(dto);
        newWorkspace.setId(oldWorkspace.getId());
        newWorkspace.setCreationDate(oldWorkspace.getCreationDate());
        newWorkspace.setUpdateDate(LocalDateTime.now());

        return workspaceRepository.save(newWorkspace);
    }

    public void deleteWorkspaceById(Long id) {
        workspaceRepository.deleteById(id);
    }

    public User addUserToWorkspace(Long userId, Long workspaceId) throws Exception {
        UserWorkspace userWorkspace = new UserWorkspace();
        userWorkspace.setOwner(false);
        userWorkspace.setUser(userRepository.findById(userId).orElseThrow(() -> new Exception("User not found")));
        userWorkspace.setWorkspace(workspaceRepository.findById(workspaceId).orElseThrow(() -> new Exception("Workspace not found")));

        UserWorkspaceId userWorkspaceId = new UserWorkspaceId();
        userWorkspaceId.setUserId(userId);
        userWorkspaceId.setWorkspaceId(workspaceId);

        userWorkspace.setId(userWorkspaceId);

        userWorkspaceRepository.save(userWorkspace);

        return userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));
    }
}
