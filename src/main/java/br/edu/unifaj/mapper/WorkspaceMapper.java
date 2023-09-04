package br.edu.unifaj.mapper;

import br.edu.unifaj.domain.Workspace;
import br.edu.unifaj.dto.model.WorkspaceDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WorkspaceMapper {

    WorkspaceMapper INSTANCE = Mappers.getMapper(WorkspaceMapper.class);

    Workspace WorkspaceDtoToWorkspace(WorkspaceDto Dto);
}
