package ua.knu.fit.sydorenko.secureapi.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import ua.knu.fit.sydorenko.secureapi.entity.UserEntity;
import ua.knu.fit.sydorenko.secureapi.entity.dto.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto map(UserEntity userEntity);

    @InheritInverseConfiguration
    UserEntity map(UserDto userDto);
}
