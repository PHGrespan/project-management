package br.edu.unifaj.dto;

import br.edu.unifaj.entity.Project;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link Project}
 */

@Data
public class ProjectDto implements Serializable {
    String name;
    String description;
    Long idWorkspace;
    Integer workspacePosition;
}