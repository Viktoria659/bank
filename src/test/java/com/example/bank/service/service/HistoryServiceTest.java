package com.example.bank.service.service;

import com.example.bank.dto.HistoryDto;
import com.example.bank.service.HistoryService;
import com.example.bank.service.util.AbstractIntegrationTest;
import com.example.bank.util.error.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class HistoryServiceTest extends AbstractIntegrationTest {

    @Autowired
    HistoryService service;

    @Test
    public void getHistory_error() {
        Long id = 1L;

        Exception exception = assertThrows(NotFoundException.class, () ->
                service.getHistory(id));

        String expectedMessage = String.format("Object with id: %s does not exist", id);
        String actualMessage = exception.getMessage();

        assertNotNull(actualMessage);
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void getHistory_validId() {
        Long id = createHistoryDto().getAccountId();

        Optional<List<HistoryDto>> res = service.getHistory(id);

        assertTrue(res.isPresent());
        assertTrue(res.get().size() > 0);
    }

    @Test
    public void getAllHistory() {
        Optional<List<HistoryDto>> res = service.getAllHistory();

        assertTrue(res.isPresent());
        assertTrue(res.get().size() > 0);
    }

    public HistoryDto createHistoryDto() {
        return service.getAllHistory().stream().findAny().get().iterator().next();
    }
}
