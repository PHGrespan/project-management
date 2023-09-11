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

    public Project insert(ProjectDto dto) throws Exception {
        Project newProject = ProjectMapper.INSTANCE.projectDtoToProject(dto);
        Workspace workspace = workspaceRepository.findById(newProject.getWorkspace().getId()).orElseThrow(() -> new Exception("Workspace not found"));

        if (newProject.getWorkspacePosition() > workspace.getProjects().size() + 1) {
            throw new Exception("Catalog must be in projectPosition between " + 1 + " and " + (workspace.getProjects().size() + 1));
        }

        projectRepository.updateAllProjectsIncrementWorkspacePositionByWorkspaceIdAndWorkspacePositionBetween(workspace.getId(), newProject.getWorkspacePosition(), projectRepository.findMaxWorkspacePositionByWorkspaceId(workspace.getId()).get(0), 1);

        newProject.setWorkspace(workspace);

        return projectRepository.save(newProject);
    }

    public Project update(Long id, ProjectDto dto) throws Exception {
        Workspace workspace = workspaceRepository.findById(dto.getIdWorkspace()).orElseThrow(() -> new Exception("Workspace not found"));
        Project oldProject = projectRepository.findById(id).orElseThrow(() -> new Exception("Project not found"));

        Project newProject = ProjectMapper.INSTANCE.projectDtoToProject(dto);

        // Validations
        // Same Workspace
        if ((newProject.getWorkspace().getId().equals(oldProject.getWorkspace().getId()) && newProject.getWorkspacePosition() > workspace.getProjects().size())) {
            throw new Exception("Project must be in projectPosition between " + 1 + " and " + (workspace.getProjects().size()));

            // Different Workspace
        } else if (newProject.getWorkspacePosition() > workspace.getProjects().size() + 1) {
            throw new Exception("Project must be in projectPosition between " + 1 + " and " + (workspace.getProjects().size() + 1));
        }

        newProject.setId(oldProject.getId());
        Integer oldWorkspacePosition = oldProject.getWorkspacePosition();
        Integer newWorkspacePosition = newProject.getWorkspacePosition();

        // Move Project to workspacePosition 0
        oldProject.setWorkspacePosition(0);
        projectRepository.save(oldProject);

        // Same Workspace
        if (newProject.getWorkspace().getId().equals(oldProject.getWorkspace().getId())) {

            // Change workspacePosition
            if (!newWorkspacePosition.equals(oldWorkspacePosition)) {

                // new workspacePosition > old workspacePosition
                if (newWorkspacePosition > oldWorkspacePosition) {
                    // Decreases 1 in the workspacePosition of the Projects that should be after the old Project and before the new Project
                    projectRepository.updateAllProjectsDecrementWorkspacePositionByWorkspaceIdAndWorkspacePositionBetween(newProject.getWorkspace().getId(), oldWorkspacePosition + 1, newWorkspacePosition, 1);

                    // new workspacePosition < old workspacePosition
                } else {
                    // Increments 1 in the workspacePosition of the Projects that should be after the old Project and before the new Project
                    projectRepository.updateAllProjectsIncrementWorkspacePositionByWorkspaceIdAndWorkspacePositionBetween(newProject.getWorkspace().getId(), newWorkspacePosition, oldWorkspacePosition - 1, 1);
                }
            }

            // Different Workspace
        } else {
            // Decreases 1 in the workspacePosition of Projects that have workspacePosition before the old Project
            projectRepository.updateAllProjectsDecrementWorkspacePositionByWorkspaceIdAndWorkspacePositionBetween(oldProject.getWorkspace().getId(), oldWorkspacePosition + 1, projectRepository.findMaxWorkspacePositionByWorkspaceId(oldProject.getWorkspace().getId()).get(0), 1);
            // Increments 1 in the workspacePosition of the Projects that should be after the new Project
            projectRepository.updateAllProjectsIncrementWorkspacePositionByWorkspaceIdAndWorkspacePositionBetween(newProject.getWorkspace().getId(), dto.getWorkspacePosition(), projectRepository.findMaxWorkspacePositionByWorkspaceId(newProject.getWorkspace().getId()).get(0), 1);
        }

        return projectRepository.save(newProject);
    }

    public void deleteProjectById(Long id) throws Exception {
        Project project = projectRepository.findById(id).orElseThrow(() -> new Exception("Catalog not found"));
        projectRepository.deleteById(id);
        projectRepository.updateAllProjectsDecrementWorkspacePositionByWorkspaceIdAndWorkspacePositionBetween(project.getWorkspace().getId(), project.getWorkspacePosition() + 1, projectRepository.findMaxWorkspacePositionByWorkspaceId(project.getWorkspace().getId()).get(0), 1);
    }
}
