package br.edu.unifaj.controller;

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
public class WorkspaceController {

    @Autowired
    WorkspaceService workspaceService;

    @JsonView(View.Workspace.class)
    @GetMapping("/users/{userId}/workspaces")
    public ResponseEntity<User> getAllWorkspacesByUserId(@PathVariable(value = "userId") Long userId) throws Exception {
        return new ResponseEntity<>(workspaceService.findAllWorkspacesByUserId(userId), HttpStatus.OK);
    }

    @JsonView(View.Workspace.class)
    @PostMapping("/users/{userId}/workspaces")
    public ResponseEntity<Workspace> insertWorkspaceByUserId(@PathVariable(value = "userId") Long userId, @Valid @RequestBody WorkspaceDto dto) throws Exception {
        return new ResponseEntity<>(workspaceService.save(userId, dto), HttpStatus.CREATED);
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
