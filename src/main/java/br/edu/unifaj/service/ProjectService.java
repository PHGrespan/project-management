package br.edu.unifaj.service;

import br.edu.unifaj.dto.ProjectDto;
import br.edu.unifaj.entity.Project;
import br.edu.unifaj.entity.Workspace;
import br.edu.unifaj.mapper.ProjectMapper;
import br.edu.unifaj.repository.ProjectRepository;
import br.edu.unifaj.repository.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    WorkspaceRepository workspaceRepository;

    public Project findProjectWithCatalogsByProjectId(Long projectId) throws Exception {
        return projectRepository.findById(projectId).orElseThrow(() -> new Exception("Project not found"));
    }

    public Project save(ProjectDto dto) throws Exception {
        Project newProject = ProjectMapper.INSTANCE.projectDtoToProject(dto);

        Workspace workspace = workspaceRepository.findById(newProject.getWorkspace().getId()).orElseThrow(() -> new Exception("Project not found"));

        newProject.setWorkspace(workspace);

        return projectRepository.save(newProject);
    }

    public Project update(Long id, ProjectDto dto) throws Exception {
        Project oldProject = projectRepository.findById(id).orElseThrow(() -> new Exception("Project not found"));

        Project newProject = ProjectMapper.INSTANCE.projectDtoToProject(dto);
        newProject.setId(oldProject.getId());

        return projectRepository.save(newProject);
    }

    public void deleteProjectById(Long id) {
        projectRepository.deleteById(id);
    }
}
