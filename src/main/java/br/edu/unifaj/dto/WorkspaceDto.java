package br.edu.unifaj.dto;

import br.edu.unifaj.entity.Workspace;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link Workspace}
 */
@Data
public class WorkspaceDto implements Serializable {

    @NotBlank
    @Size(min = 1)
    String name;

    String description;
}