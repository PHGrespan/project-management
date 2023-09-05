package br.edu.unifaj.dto;

import br.edu.unifaj.entity.Catalog;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link Catalog}
 */

@Data
public class CatalogDto implements Serializable {

    @NotBlank
    @Size(min = 1)
    String name;

    @NotNull
    Long idProject;

    @NotNull
    Integer projectPosition;
}