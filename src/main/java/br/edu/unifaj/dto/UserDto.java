package br.edu.unifaj.dto;

import br.edu.unifaj.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
@Data
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