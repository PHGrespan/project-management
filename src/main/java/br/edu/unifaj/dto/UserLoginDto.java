package br.edu.unifaj.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;

import java.io.Serializable;

@Value
public class UserLoginDto implements Serializable {

    @Email
    String email;

    @NotBlank
    String pass;

}