package br.edu.unifaj.dto;


import br.edu.unifaj.entity.Card;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link Card}
 */

@Data
public class CardDto implements Serializable {

    String name;
    String description;
    Long idCatalog;
    Integer listPosition;
}