package br.edu.unifaj.controller;

import br.edu.unifaj.domain.User;
import br.edu.unifaj.domain.Workspace;
import br.edu.unifaj.dto.model.UserDto;
import br.edu.unifaj.dto.model.WorkspaceDto;
import br.edu.unifaj.service.UserService;
import br.edu.unifaj.service.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @PostMapping
    public ResponseEntity<Workspace> insertWorkspace(@Valid @RequestBody WorkspaceDto dto) {
        return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
    }
}
