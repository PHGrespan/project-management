package br.edu.unifaj.mapper;


import br.edu.unifaj.dto.CardDto;
import br.edu.unifaj.entity.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CardMapper {

    CardMapper INSTANCE = Mappers.getMapper(CardMapper.class);

    @Mapping(target="catalog.id", source="idCatalog")
    Card cardDtoToCard(CardDto dto);
}
