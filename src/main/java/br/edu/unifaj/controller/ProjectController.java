package br.edu.unifaj.controller;

import br.edu.unifaj.dto.ProjectDto;
import br.edu.unifaj.entity.Project;
import br.edu.unifaj.entity.Workspace;
import br.edu.unifaj.service.ProjectService;
import br.edu.unifaj.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @JsonView(View.Project.class)
    @GetMapping("/workspaces/{workspaceId}/projects")
    public ResponseEntity<Workspace> findAllProjectsByWorkspaceId(@PathVariable(value = "workspaceId") Long workspaceId) throws Exception {
        return new ResponseEntity<>(projectService.findAllProjectsByWorkspaceId(workspaceId), HttpStatus.OK);
    }

    @JsonView(View.Project.class)
    @PostMapping("/projects")
    public ResponseEntity<Project> insertProjectByWorkspaceId(@Valid @RequestBody ProjectDto dto) throws Exception {
        return new ResponseEntity<>(projectService.save(dto), HttpStatus.CREATED);
    }
    @JsonView(View.Project.class)
    @PutMapping("/projects/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable(value = "id") Long id, @Valid @RequestBody ProjectDto dto) throws Exception {
        return new ResponseEntity<>(projectService.update(id, dto), HttpStatus.OK);
    }

}
