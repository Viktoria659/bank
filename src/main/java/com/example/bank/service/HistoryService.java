package com.example.bank.service;

import com.example.bank.dto.HistoryDto;

import java.util.Optional;
import java.util.Set;

public interface HistoryService {
    Optional<Set<HistoryDto>> getHistory(Long id);

    Optional<Set<HistoryDto>> getAllHistory();
}
