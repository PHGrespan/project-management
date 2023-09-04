package br.edu.unifaj.dto.model;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link br.edu.unifaj.domain.User}
 */
@Value
public class UserDto implements Serializable {
    String name;
    String email;
    String pass;
}