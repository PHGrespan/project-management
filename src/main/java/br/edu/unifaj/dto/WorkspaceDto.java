package br.edu.unifaj.dto;

import br.edu.unifaj.entity.Workspace;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link Workspace}
 */
@Data
public class WorkspaceDto implements Serializable {
    String name;
    String description;
}