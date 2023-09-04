package br.edu.unifaj.service;

import br.edu.unifaj.domain.Workspace;
import br.edu.unifaj.dto.model.WorkspaceDto;
import br.edu.unifaj.mapper.WorkspaceMapper;
import br.edu.unifaj.repository.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
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

        newWorkspace.setCreationDate(Instant.now());
        newWorkspace.setUpdateDate(Instant.now());

        return repository.save(newWorkspace);
    }
}
