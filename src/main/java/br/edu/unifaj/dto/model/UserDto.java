package br.edu.unifaj.dto.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link br.edu.unifaj.domain.User}
 */
@Value
public class UserDto implements Serializable {

    @NotBlank
    @Size(min = 2)
    String name;

    @Email
    String email;

    @NotBlank
    @Size(min = 8)
    String pass;
}