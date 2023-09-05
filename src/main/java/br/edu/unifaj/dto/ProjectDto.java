package br.edu.unifaj.dto;

import br.edu.unifaj.entity.Project;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link Project}
 */

@Data
public class ProjectDto implements Serializable {

    @NotBlank
    @Size(min = 1)
    String name;

    String description;

    @NotNull
    Long idWorkspace;

    @NotNull
    Integer workspacePosition;
}