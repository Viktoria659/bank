package com.example.bank.controller;

import com.example.bank.dto.ClientDto;
import com.example.bank.service.ClientService;
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

import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "ClientController")
public class ClientController {
    ClientService service;

    @Operation(summary = "Поиск клиента по id")
    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> findById(@Parameter(description = "Индентификатор клиента", example = "1")
                                              @PathVariable final Long id) {
        log.info("Search client by id");
        return service.getClient(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Поиск клиента по user's username")
    @GetMapping("username/{username}")
    public ResponseEntity<ClientDto> findByUsername(@Parameter(description = "Username пользователя", example = "username")
                                                    @PathVariable final String username) {
        log.info("Search client by user's username");
        return service.getClientByUsername(username)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Поиск всех клиентов")
    @GetMapping("/get-clients")
    public ResponseEntity<Set<ClientDto>> getClients() {
        log.info("Search all clients");
        return service.getClients()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
