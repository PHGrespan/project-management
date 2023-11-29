package br.edu.unifaj.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class UserWorkspaceDto {

    @NotNull
    Long userId;

    @NotNull
    Long workspaceId;
}
