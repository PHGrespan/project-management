package br.edu.unifaj.mapper;

import br.edu.unifaj.entity.User;
import br.edu.unifaj.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User UserDtoToUser(UserDto dto);
}
