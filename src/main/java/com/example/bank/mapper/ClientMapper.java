package com.example.bank.mapper;

import com.example.bank.dto.AccountDto;
import com.example.bank.dto.ClientDto;
import com.example.bank.entities.AccountEntity;
import com.example.bank.entities.ClientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper
public interface ClientMapper {

    @Mapping(target = "user.client", ignore = true)
    ClientDto entityToDto(ClientEntity entity);

    ClientEntity dtoToEntity(ClientDto dto);

    Set<ClientDto> entitySetToDtoSet(List<ClientEntity> entities);

    Set<AccountDto> entitySetToDtoSetAccount(List<AccountEntity> entities);
}
