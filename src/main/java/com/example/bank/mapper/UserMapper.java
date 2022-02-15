package com.example.bank.mapper;

import com.example.bank.dto.UserDto;
import com.example.bank.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper
public interface UserMapper {

    @Mapping(target = "client.user", ignore = true)
    UserDto entityToDto(UserEntity entity);

    @Mapping(target = "active", source = "active", defaultValue = "true")
    UserEntity dtoToEntity(UserDto dto);

    Set<UserDto> entitySetToDtoSet(List<UserEntity> entities);

    Set<UserDto> entitySetToDtoSet(Set<UserEntity> entities);
}
