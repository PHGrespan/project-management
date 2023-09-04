package br.edu.unifaj.service;

import br.edu.unifaj.domain.User;
import br.edu.unifaj.domain.UserWorkspace;
import br.edu.unifaj.domain.UserWorkspaceId;
import br.edu.unifaj.domain.Workspace;
import br.edu.unifaj.dto.model.WorkspaceDto;
import br.edu.unifaj.mapper.WorkspaceMapper;
import br.edu.unifaj.repository.UserRepository;
import br.edu.unifaj.repository.UserWorkspaceRepository;
import br.edu.unifaj.repository.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WorkspaceService {

    @Autowired
    WorkspaceRepository repository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserWorkspaceRepository userWorkspaceRepository;

    public List<Workspace> findAll() {
        return repository.findAll();
    }

    @Transactional
    public Workspace save(Long userId, WorkspaceDto dto) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));

        Workspace newWorkspace= WorkspaceMapper.INSTANCE.WorkspaceDtoToWorkspace(dto);

        newWorkspace.setCreationDate(LocalDateTime.now());
        newWorkspace.setUpdateDate(LocalDateTime.now());

        newWorkspace = repository.save(newWorkspace);


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
        Workspace oldWorkspace = repository.findById(id).orElseThrow(() -> new Exception("Workspace not found"));

        Workspace newWorkspace = WorkspaceMapper.INSTANCE.WorkspaceDtoToWorkspace(dto);
        newWorkspace.setId(oldWorkspace.getId());
        newWorkspace.setCreationDate(oldWorkspace.getCreationDate());
        newWorkspace.setUpdateDate(LocalDateTime.now());

        return repository.save(newWorkspace);
    }
}
