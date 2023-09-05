package br.edu.unifaj.dto;

import br.edu.unifaj.entity.Catalog;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link Catalog}
 */

@Data
public class CatalogDto implements Serializable {
    String name;
    Long idProject;
    Integer projectPosition;
}