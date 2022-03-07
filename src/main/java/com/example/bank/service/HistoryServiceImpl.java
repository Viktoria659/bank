package com.example.bank.service;

import com.example.bank.dto.HistoryDto;
import com.example.bank.mapper.HistoryMapper;
import com.example.bank.repo.HistoryRepo;
import com.example.bank.util.error.NotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HistoryServiceImpl implements HistoryService {

    HistoryRepo repo;
    HistoryMapper mapper = Mappers.getMapper(HistoryMapper.class);

    @Override
    public Optional<List<HistoryDto>> getHistory(Long id) {
        log.info("Start found history account by id: {}", id);
        List<HistoryDto> dtoList = mapper.entityToDto(repo.findAllByHistoryId_AccountIdOrderByHistoryId_Rev(id));
        if (dtoList.size() > 0) {
            return Optional.of(dtoList);
        } else {
            log.warn("Account by id: {} does not exist!", id);
            throw new NotFoundException(id);
        }
    }

    @Override
    public Optional<List<HistoryDto>> getAllHistory() {
        log.info("Start found all accounts");
        List<HistoryDto> dtoList = mapper.entityToDto(repo.findAll());
        if (dtoList.isEmpty()) {
            log.error("Objects do not exists!");
            return Optional.empty();
        } else {
            log.info("Was found {} accounts", dtoList.size());
            return Optional.of(dtoList);
        }
    }
}
