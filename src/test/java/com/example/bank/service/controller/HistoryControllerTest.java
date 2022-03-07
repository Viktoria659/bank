package com.example.bank.service.controller;

import com.example.bank.dto.AccountDto;
import com.example.bank.service.AccountService;
import com.example.bank.service.util.AbstractControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.example.bank.util.Constant.PLUS;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HistoryControllerTest extends AbstractControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mvc;
    @Autowired
    AccountService service;
    private static final String partOfPath = "/history";

    @BeforeEach
    public void init() {
        mvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    public void getHistory_Ok() throws Exception {
        AccountDto accountDto = createAccountDto();
        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .get(partOfPath + "/{id}", accountDto.getAccountId()))
                .andExpect(status().isOk())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void getHistory_NotFound() throws Exception {

        MvcResult res = mvc.perform(MockMvcRequestBuilders
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
