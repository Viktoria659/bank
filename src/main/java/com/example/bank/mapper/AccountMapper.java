package com.example.bank.mapper;

import com.example.bank.dto.AccountDto;
import com.example.bank.entities.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper
public interface AccountMapper {

    @Mapping(target = "client.user.client", ignore = true)
    AccountDto entityToDto(AccountEntity entity);

    @Mapping(target = "client.accounts", ignore = true)
    @Mapping(target = "client.user.client", ignore = true)
    AccountEntity dtoToEntity(AccountDto dto);

    Set<AccountDto> entitySetToDtoSet(List<AccountEntity> entities);

    Set<AccountDto> entitySetToDtoSet(Set<AccountEntity> entities);
}
