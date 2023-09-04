package br.edu.unifaj.controller;

import br.edu.unifaj.domain.Workspace;
import br.edu.unifaj.dto.model.WorkspaceDto;
import br.edu.unifaj.service.WorkspaceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workspaces")
public class WorkspaceController {

    @Autowired
    WorkspaceService service;

    @GetMapping
    public ResponseEntity<List<Workspace>> getAllWorkspaces() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Workspace> insertWorkspace(@PathVariable(value = "userId") Long userId, @Valid @RequestBody WorkspaceDto dto) throws Exception {
        return new ResponseEntity<>(service.save(userId, dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Workspace> updateWorkspace(@PathVariable(value = "id") Long id, @Valid @RequestBody WorkspaceDto dto) throws Exception {
        return new ResponseEntity<>(service.update(id, dto), HttpStatus.OK);
    }
}
