package br.edu.unifaj.dto.model;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link br.edu.unifaj.domain.Workspace}
 */
@Value
public class WorkspaceDto implements Serializable {
    String name;
    String description;
}