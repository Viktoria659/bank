package com.example.bank.service.controller;

import com.example.bank.dto.AccountDto;
import com.example.bank.service.AccountService;
import com.example.bank.service.util.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.example.bank.util.Constant.PLUS;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HistoryControllerTest extends AbstractIntegrationTest {

    @Autowired
    AccountService service;
    private static final String partOfPath = "/history";

    @Test
    public void getHistory_Ok() throws Exception {
        AccountDto accountDto = createAccountDto();
        MvcResult res = mvcWithoutAuth.perform(MockMvcRequestBuilders
                        .get(partOfPath + "/{id}", accountDto.getAccountId()))
                .andExpect(status().isOk())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void getHistory_NotFound() throws Exception {

        MvcResult res = mvcWithoutAuth.perform(MockMvcRequestBuilders
                        .get(partOfPath + "/{id}", "1"))
                .andExpect(status().isNotFound())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    private AccountDto createAccountDto() {
        return service.addAccount()
                .map(dto -> service.update(dto.getAccountId(), 100L, PLUS, "PLUS").get())
                .orElseThrow(() -> new RuntimeException("Не удалось создать аккаунт"));
    }
}
