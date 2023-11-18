package br.edu.unifaj.controller.rest;

import br.edu.unifaj.dto.ProjectDto;
import br.edu.unifaj.dto.ResponseDto;
import br.edu.unifaj.entity.Project;
import br.edu.unifaj.service.ProjectService;
import br.edu.unifaj.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:63343")
public class ProjectRestController {

    @Autowired
    ProjectService projectService;

    @JsonView(View.Catalog.class)
    @GetMapping("/projects/{projectId}/catalogs")
    public ResponseEntity<Project> findProjectWithCatalogsByProjectId(@PathVariable(value = "projectId") Long projectId) throws Exception {
        return new ResponseEntity<>(projectService.findById(projectId), HttpStatus.OK);
    }

    @JsonView(View.Project.class)
    @PostMapping("/projects")
    public ResponseEntity<Project> insertProject(@Valid @RequestBody ProjectDto dto) throws Exception {
        return new ResponseEntity<>(projectService.insert(dto), HttpStatus.CREATED);
    }
    @JsonView(View.Project.class)
    @PutMapping("/projects/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable(value = "id") Long id, @Valid @RequestBody ProjectDto dto) throws Exception {
        return new ResponseEntity<>(projectService.update(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/projects/{id}")
    public ResponseEntity<ResponseDto> deleteCardById(@PathVariable(value = "id") Long id) throws Exception {
        projectService.deleteProjectById(id);
        return new ResponseEntity<>(new ResponseDto("Project, Catalogs and Cards deleted"), HttpStatus.OK);
    }
}
