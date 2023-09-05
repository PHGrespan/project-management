package br.edu.unifaj.dto;


import br.edu.unifaj.entity.Card;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link Card}
 */

@Data
public class CardDto implements Serializable {

    @NotBlank
    @Size(min = 1)
    String name;

    String description;

    @NotNull
    Long idCatalog;

    @NotNull
    Integer listPosition;
}