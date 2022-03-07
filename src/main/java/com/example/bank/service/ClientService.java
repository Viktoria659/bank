package com.example.bank.service;

import com.example.bank.dto.ClientDto;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    Optional<ClientDto> getClient(Long id);

    Optional<ClientDto> getClientByUsername(String username);

    Optional<List<ClientDto>> getClients();
}
