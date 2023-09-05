package br.edu.unifaj.service;

import br.edu.unifaj.dto.ProjectDto;
import br.edu.unifaj.entity.Project;
import br.edu.unifaj.entity.Workspace;
import br.edu.unifaj.mapper.ProjectMapper;
import br.edu.unifaj.repository.ProjectRepository;
import br.edu.unifaj.repository.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    WorkspaceRepository workspaceRepository;

    public Project save(ProjectDto dto) throws Exception {
        Project newProject = ProjectMapper.INSTANCE.ProjectDtoToProject(dto);

        Workspace workspace = workspaceRepository.findById(newProject.getWorkspace().getId()).orElseThrow(() -> new Exception("Workspace not found"));

        newProject.setWorkspace(workspace);

        return projectRepository.save(newProject);
    }

    public Project update(Long id, ProjectDto dto) throws Exception {
        Project oldProject = projectRepository.findById(id).orElseThrow(() -> new Exception("User not found"));

        Project newProject = ProjectMapper.INSTANCE.ProjectDtoToProject(dto);
        newProject.setId(oldProject.getId());

        return projectRepository.save(newProject);
    }

    public Workspace findAllProjectsByWorkspaceId(Long workspaceId) throws Exception {
        return workspaceRepository.findById(workspaceId).orElseThrow(() -> new Exception("User not found"));
    }
}
