package com.example.bank.service;

import com.example.bank.dto.ClientDto;

import java.util.Optional;
import java.util.Set;

public interface ClientService {
    Optional<ClientDto> getClient(Long id);

    Optional<ClientDto> getClientByUsername(String username);

    Optional<Set<ClientDto>> getClients();
}
