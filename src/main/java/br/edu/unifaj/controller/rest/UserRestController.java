package br.edu.unifaj.controller.rest;

import br.edu.unifaj.dto.LoginResponseDto;
import br.edu.unifaj.dto.ResponseDto;
import br.edu.unifaj.dto.UserDto;
import br.edu.unifaj.dto.UserLoginDto;
import br.edu.unifaj.entity.User;
import br.edu.unifaj.service.TokenService;
import br.edu.unifaj.service.UserService;
import br.edu.unifaj.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserRestController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

    @JsonView(View.Workspace.class)
    @GetMapping("/users/{userId}/workspaces")
    public ResponseEntity<User> findUserWithWorkspacesByUserId(@PathVariable(value = "userId") Long userId) throws Exception {
        return new ResponseEntity<>(userService.findById(userId), HttpStatus.OK);
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

    @PostMapping("/users/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody UserLoginDto dto) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPass());
        Authentication authentication = authenticationManager.authenticate(usernamePassword);

        String token = tokenService.generateToken((User) authentication.getPrincipal());
        return new ResponseEntity<>(new LoginResponseDto(token), HttpStatus.OK);
    }

    @JsonView(View.User.class)
    @PostMapping("/users/register")
    public ResponseEntity<User> insertUser(@Valid @RequestBody UserDto dto) throws Exception {
        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.getPass());
        UserDto userWithEncryptedPassword = new UserDto(dto.getName(), dto.getEmail(), encryptedPassword);
        User user = userService.save(userWithEncryptedPassword);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
