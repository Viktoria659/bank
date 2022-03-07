package com.example.bank.controller;

import com.example.bank.dto.HistoryDto;
import com.example.bank.service.HistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/history")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "HistoryController")
public class HistoryController {

    HistoryService service;

    @Operation(summary = "История операций со счетом")
    @GetMapping("/{id}")
    public ResponseEntity<List<HistoryDto>> getHistory(@Parameter(description = "Идентификатор счёта")
                                                      @PathVariable final Long id) {
        log.info("Search of account's history");
        return service.getHistory(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
