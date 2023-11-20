package br.edu.unifaj.dto;

import br.edu.unifaj.entity.Project;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Project}
 */

@Value
public class ProjectDto implements Serializable {

    @NotBlank
    @Size(min = 1)
    String name;

    String description;

    @NotNull
    @Min(1)
    Long idWorkspace;

    @NotNull
    Integer workspacePosition;
}