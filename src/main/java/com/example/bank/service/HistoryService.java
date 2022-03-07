package com.example.bank.service;

import com.example.bank.dto.HistoryDto;

import java.util.List;
import java.util.Optional;

public interface HistoryService {
    Optional<List<HistoryDto>> getHistory(Long id);

    Optional<List<HistoryDto>> getAllHistory();
}
