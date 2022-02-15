package com.example.bank.service.service;

import com.example.bank.dto.ClientDto;
import com.example.bank.service.ClientService;
import com.example.bank.service.util.AbstractIntegrationTest;
import com.example.bank.util.error.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.Set;

import static com.example.bank.service.util.TestUtil.toJson;
import static org.junit.jupiter.api.Assertions.*;

public class ClientServiceTest extends AbstractIntegrationTest {

    @Autowired
    ClientService clientService;

    @Test
    public void getClientByUsername_error() {
        String username = "1234";

        Exception exception = assertThrows(NotFoundException.class, () -> clientService.getClientByUsername(username));

        String expectedMessage = String.format("Object with username: %s does not exist", username);
        String actualMessage = exception.getMessage();

        assertNotNull(actualMessage);
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void getClientIdByUsername() {
        ClientDto clientDto = getClientDto();
        clientDto.getAccounts().clear();

        Optional<ClientDto> res = clientService.getClientByUsername(clientDto.getUser().getUsername());

        assertTrue(res.isPresent());
        assertEquals(toJson(clientDto), toJson(res.get()));
    }

    @Test
    public void getClient_withInvalidId_error() {
        Long id = 15L;

        Exception exception = assertThrows(NotFoundException.class, () -> clientService.getClient(id));

        String expectedMessage = String.format("Object with id: %s does not exist", id);
        String actualMessage = exception.getMessage();

        assertNotNull(actualMessage);
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void getClient() {
        ClientDto clientDto = getClientDto();

        Optional<ClientDto> res = clientService.getClient(clientDto.getId());

        assertTrue(res.isPresent());
        assertEquals(toJson(clientDto), toJson(res.get()));
    }

    @Test
    public void getClients() {
        Optional<Set<ClientDto>> clients = clientService.getClients();

        assertTrue(clients.isPresent());
        assertTrue(clients.get().size() > 0);
    }

    public ClientDto getClientDto() {
        return clientService.getClients().stream().findAny().get().iterator().next();
    }
}
