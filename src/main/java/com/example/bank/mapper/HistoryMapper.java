package com.example.bank.mapper;

import com.example.bank.dto.HistoryDto;
import com.example.bank.entities.HistoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper
public interface HistoryMapper {

    @Mapping(source = "historyId.accountId", target = "accountId")
    @Mapping(source = "historyId.rev", target = "rev")
    HistoryDto entityToDto(HistoryEntity entity);

    HistoryEntity dtoToEntity(HistoryDto dto);

    Set<HistoryDto> entitySetToDtoSet(List<HistoryEntity> entities);

    Set<HistoryDto> entitySetToDtoSet(Set<HistoryEntity> entities);
}
