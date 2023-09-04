package br.edu.unifaj.controller;

import br.edu.unifaj.domain.User;
import br.edu.unifaj.dto.model.UserDto;
import br.edu.unifaj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService service;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> insertUser(@Valid @RequestBody UserDto dto) {
        return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{email}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "email") String email, @Valid @RequestBody UserDto dto) throws Exception {
        return new ResponseEntity<>(service.update(email, dto), HttpStatus.CREATED);
    }
}
