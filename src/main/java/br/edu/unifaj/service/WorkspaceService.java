package br.edu.unifaj.service;

import br.edu.unifaj.domain.Workspace;
import br.edu.unifaj.dto.model.WorkspaceDto;
import br.edu.unifaj.mapper.WorkspaceMapper;
import br.edu.unifaj.repository.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WorkspaceService {

    @Autowired
    WorkspaceRepository repository;

    public List<Workspace> findAll() {
        return repository.findAll();
    }

    public Workspace save(WorkspaceDto dto) {
        Workspace newWorkspace= WorkspaceMapper.INSTANCE.WorkspaceDtoToWorkspace(dto);

        newWorkspace.setCreationDate(LocalDateTime.now());
        newWorkspace.setUpdateDate(LocalDateTime.now());

        return repository.save(newWorkspace);
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
