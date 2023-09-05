package br.edu.unifaj.mapper;

import br.edu.unifaj.dto.ProjectDto;
import br.edu.unifaj.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProjectMapper {

    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    @Mapping(target="workspace.id", source="idWorkspace")
    Project ProjectDtoToProject(ProjectDto dto);
}
