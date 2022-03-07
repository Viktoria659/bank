package com.example.bank.mapper;

import com.example.bank.dto.ClientDto;
import com.example.bank.entities.ClientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface ClientMapper {

    @Mapping(target = "user.client", ignore = true)
    ClientDto entityToDto(ClientEntity entity);

    ClientEntity dtoToEntity(ClientDto dto);

    List<ClientDto> entityToDto(List<ClientEntity> entities);
}
