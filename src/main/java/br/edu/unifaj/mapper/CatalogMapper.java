package br.edu.unifaj.mapper;

import br.edu.unifaj.dto.CatalogDto;
import br.edu.unifaj.entity.Catalog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CatalogMapper {

    CatalogMapper INSTANCE = Mappers.getMapper(CatalogMapper.class);

    @Mapping(target="project.id", source="idProject")
    Catalog catalogDtoToCatalog(CatalogDto dto);
}
