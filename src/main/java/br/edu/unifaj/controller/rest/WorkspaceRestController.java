package br.edu.unifaj.controller.rest;

import br.edu.unifaj.dto.ResponseDto;
import br.edu.unifaj.dto.UserWorkspaceDto;
import br.edu.unifaj.dto.WorkspaceDto;
import br.edu.unifaj.entity.User;
import br.edu.unifaj.entity.Workspace;
import br.edu.unifaj.service.WorkspaceService;
import br.edu.unifaj.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:63342", "http://localhost:63343"})
public class WorkspaceRestController {

    @Autowired
    WorkspaceService workspaceService;

    @JsonView(View.Card.class)
    @GetMapping("/workspaces/{workspaceId}")
    public ResponseEntity<Workspace> findWorkspaceWithProjectsCatalogsAndCardsByWorkspaceId(@PathVariable(value = "workspaceId") Long workspaceId) throws Exception {
        return new ResponseEntity<>(workspaceService.findById(workspaceId), HttpStatus.OK);
    }

    @JsonView(View.Project.class)
    @GetMapping("/workspaces/{workspaceId}/projects")
    public ResponseEntity<Workspace> findWorkspaceWithProjectsByWorkspaceId(@PathVariable(value = "workspaceId") Long workspaceId) throws Exception {
        return new ResponseEntity<>(workspaceService.findById(workspaceId), HttpStatus.OK);
    }

    @JsonView(View.Workspace.class)
    @PostMapping("/users/{userId}/workspaces")
    public ResponseEntity<Workspace> insertWorkspaceByUserId(@PathVariable(value = "userId") Long userId, @Valid @RequestBody WorkspaceDto dto) throws Exception {
        return new ResponseEntity<>(workspaceService.insert(userId, dto), HttpStatus.CREATED);
    }

    @JsonView(View.Workspace.class)
    @PutMapping("/workspaces/{id}")
    public ResponseEntity<Workspace> updateWorkspace(@PathVariable(value = "id") Long id, @Valid @RequestBody WorkspaceDto dto) throws Exception {
        return new ResponseEntity<>(workspaceService.update(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/workspaces/{id}")
    public ResponseEntity<ResponseDto> deleteWorkspaceById(@PathVariable(value = "id") Long id){
        workspaceService.deleteWorkspaceById(id);
        return new ResponseEntity<>(new ResponseDto("Workspace, Projects, Catalogs and Cards deleted"), HttpStatus.OK);
    }

    @JsonView(View.Workspace.class)
    @PatchMapping("/workspaces")
    public ResponseEntity<User> addUserToWorkspace(@Valid @RequestBody UserWorkspaceDto dto) throws Exception {
        return new ResponseEntity<>(workspaceService.addUserToWorkspace(dto.getUserId(), dto.getWorkspaceId()), HttpStatus.OK);
    }

}
