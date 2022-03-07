package com.example.bank.mapper;

import com.example.bank.dto.AccountDto;
import com.example.bank.entities.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface AccountMapper {

    @Mapping(target = "client.user.client", ignore = true)
    AccountDto entityToDto(AccountEntity entity);

    @Mapping(target = "client.accounts", ignore = true)
    @Mapping(target = "client.user.client", ignore = true)
    AccountEntity dtoToEntity(AccountDto dto);

    List<AccountDto> entityToDto(List<AccountEntity> entities);
}
