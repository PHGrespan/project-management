package br.edu.unifaj.controller.rest;

import br.edu.unifaj.dto.ResponseDto;
import br.edu.unifaj.dto.UserDto;
import br.edu.unifaj.entity.User;
import br.edu.unifaj.service.UserService;
import br.edu.unifaj.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserRestController {

    @Autowired
    UserService userService;

    @JsonView(View.Workspace.class)
    @GetMapping("/users/{userId}/workspaces")
    public ResponseEntity<User> findUserWithWorkspacesByUserId(@PathVariable(value = "userId") Long userId) throws Exception {
        return new ResponseEntity<>(userService.findById(userId), HttpStatus.OK);
    }

    @JsonView(View.User.class)
    @PostMapping("/users")
    public ResponseEntity<User> insertUser(@Valid @RequestBody UserDto dto) {
        return new ResponseEntity<>(userService.save(dto), HttpStatus.CREATED);
    }

    @JsonView(View.User.class)
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long id, @Valid @RequestBody UserDto dto) throws Exception {
        return new ResponseEntity<>(userService.update(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ResponseDto> deleteUserById(@PathVariable(value = "id") Long id){
        userService.deleteUserById(id);
        return new ResponseEntity<>(new ResponseDto("User, Workspaces, Projects, Catalogs and Cards deleted"), HttpStatus.OK);
    }
}
