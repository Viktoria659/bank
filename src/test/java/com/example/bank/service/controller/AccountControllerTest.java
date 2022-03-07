package com.example.bank.service.controller;

import com.example.bank.dto.AccountDto;
import com.example.bank.service.AccountService;
import com.example.bank.service.util.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.example.bank.util.Constant.PLUS;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountControllerTest extends AbstractIntegrationTest {

    private static final String partOfPath = "/account";

    @Autowired
    AccountService service;

    @Test
    public void addAccount_Ok() throws Exception {
        MvcResult res = mvcWithoutAuth.perform(MockMvcRequestBuilders
                        .post(partOfPath + "/add-account/{username}", "username"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.client.id").exists())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void addAccountCurrentUser_Ok() throws Exception {
        MvcResult res = mvcWithoutAuth.perform(MockMvcRequestBuilders
                        .post(partOfPath + "/add-account"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.client.id").exists())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void findById_Ok() throws Exception {
        AccountDto accountDto = createAccountDto();
        MvcResult res = mvcWithoutAuth.perform(MockMvcRequestBuilders
                        .get(partOfPath + "/{id}", accountDto.getAccountId()))
                .andExpect(status().isOk())
                .andReturn();
        deleteAccountDto(accountDto.getAccountId());

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void findById_NotFound() throws Exception {

        MvcResult res = mvcWithoutAuth.perform(MockMvcRequestBuilders
                        .get(partOfPath + "/{id}", "1"))
                .andExpect(status().isNotFound())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void getAccounts_Ok() throws Exception {

        MvcResult res = mvcWithoutAuth.perform(MockMvcRequestBuilders
                        .get(partOfPath + "/get-accounts"))
                .andExpect(status().isOk())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void getAccountsCurrent_Ok() throws Exception {

        MvcResult res = mvcWithoutAuth.perform(MockMvcRequestBuilders
                        .get(partOfPath + "/get-accounts-current-user"))
                .andExpect(status().isOk())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void getAccountsByUserUsername_Ok() throws Exception {

        MvcResult res = mvcWithoutAuth.perform(MockMvcRequestBuilders
                        .get(partOfPath + "/get-accounts/{username}", "username"))
                .andExpect(status().isOk())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void updateRefillBalance_Ok() throws Exception {
        AccountDto accountDto = createAccountDto();
        MvcResult res = mvcWithoutAuth.perform(MockMvcRequestBuilders
                        .put(partOfPath + "/refill-account-balance/{id}/{money}",
                                accountDto.getAccountId(), "50"))
                .andExpect(status().isOk())
                .andReturn();
        deleteAccountDto(accountDto.getAccountId());

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void updateRefillBalance_NotFound() throws Exception {

        MvcResult res = mvcWithoutAuth.perform(MockMvcRequestBuilders
                        .put(partOfPath + "/refill-account-balance/{id}/{money}", "1", "50"))
                .andExpect(status().isNotFound())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void updateWithdrawBalance_Ok() throws Exception {
        AccountDto accountDto = createAccountDto();

        MvcResult res = mvcWithoutAuth.perform(MockMvcRequestBuilders
                        .put(partOfPath + "/withdraw-account-balance/{id}/{money}",
                                accountDto.getAccountId(), "100"))
                .andExpect(status().isOk())
                .andReturn();
        deleteAccountDto(accountDto.getAccountId());

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void updateWithdrawBalance_NotFound() throws Exception {

        MvcResult res = mvcWithoutAuth.perform(MockMvcRequestBuilders
                        .put(partOfPath + "/withdraw-account-balance/{id}/{money}", "1", "50"))
                .andExpect(status().isNotFound())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void updateWithdrawBalance_BadRequest() throws Exception {
        AccountDto accountDto = createAccountDto();
        MvcResult res = mvcWithoutAuth.perform(MockMvcRequestBuilders
                        .put(partOfPath + "/withdraw-account-balance/{id}/{money}",
                                accountDto.getAccountId(), "50000"))
                .andExpect(status().isBadRequest())
                .andReturn();
        deleteAccountDto(accountDto.getAccountId());

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void transferBetweenAccounts_NotFound_From() throws Exception {
        AccountDto accountDto = createAccountDto();
        MvcResult res = mvcWithoutAuth.perform(MockMvcRequestBuilders
                        .put(partOfPath + "/transfer-between-accounts/{fromId}/{toId}/{money}/{comment}",
                                "1", accountDto.getAccountId(), "100", "null"))
                .andExpect(status().isNotFound())
                .andReturn();
        deleteAccountDto(accountDto.getAccountId());

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void transferBetweenAccounts_NotFound_To() throws Exception {
        AccountDto accountDto = createAccountDto();
        MvcResult res = mvcWithoutAuth.perform(MockMvcRequestBuilders
                        .put(partOfPath + "/transfer-between-accounts/{fromId}/{toId}/{money}/{comment}",
                                accountDto.getAccountId(), "1", "100", "null"))
                .andExpect(status().isNotFound())
                .andReturn();
        deleteAccountDto(accountDto.getAccountId());

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void transferBetweenAccounts_BadRequest() throws Exception {
        AccountDto accountDtoFrom = createAccountDto();
        AccountDto accountDtoTo = createAccountDto();
        MvcResult res = mvcWithoutAuth.perform(MockMvcRequestBuilders
                        .put(partOfPath + "/transfer-between-accounts/{fromId}/{toId}/{money}/{comment}",
                                accountDtoFrom.getAccountId(), accountDtoTo.getAccountId(), "1000000000", "null"))
                .andExpect(status().isBadRequest())
                .andReturn();
        deleteAccountDto(accountDtoFrom.getAccountId());
        deleteAccountDto(accountDtoTo.getAccountId());

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void transferBetweenAccounts_Ok() throws Exception {
        AccountDto accountDtoFrom = createAccountDto();
        AccountDto accountDtoTo = createAccountDto();
        MvcResult res = mvcWithoutAuth.perform(MockMvcRequestBuilders
                        .put(partOfPath + "/transfer-between-accounts/{fromId}/{toId}/{money}/{comment}",
                                accountDtoFrom.getAccountId(), accountDtoTo.getAccountId(), "50", "newComment"))
                .andExpect(status().isOk())
                .andReturn();
        deleteAccountDto(accountDtoFrom.getAccountId());
        deleteAccountDto(accountDtoTo.getAccountId());

        assertTrue(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void payForCommunalServices_NotFound_From() throws Exception {

        MvcResult res = mvcWithoutAuth.perform(MockMvcRequestBuilders
                        .put(partOfPath + "/pay-for-communal-services/{fromId}/{money}/{comment}",
                                "1", "100", "null"))
                .andExpect(status().isNotFound())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void payForCommunalServices_BadRequest() throws Exception {
        AccountDto accountDto = createAccountDto();
        MvcResult res = mvcWithoutAuth.perform(MockMvcRequestBuilders
                        .put(partOfPath + "/pay-for-communal-services/{fromId}/{money}/{comment}",
                                accountDto.getAccountId(), "100000000", "null"))
                .andExpect(status().isBadRequest())
                .andReturn();
        deleteAccountDto(accountDto.getAccountId());

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void payForCommunalServices_Ok() throws Exception {
        AccountDto accountDto = createAccountDto();
        MvcResult res = mvcWithoutAuth.perform(MockMvcRequestBuilders
                        .put(partOfPath + "/pay-for-communal-services/{fromId}/{money}/{comment}",
                                accountDto.getAccountId(), "50", "ЖКХ"))
                .andExpect(status().isOk())
                .andReturn();
        deleteAccountDto(accountDto.getAccountId());

        assertTrue(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void payForTelephone_NotFound_From() throws Exception {

        MvcResult res = mvcWithoutAuth.perform(MockMvcRequestBuilders
                        .put(partOfPath + "/pay-for-telephone/{fromId}/{money}/{comment}",
                                "1", "100", "null"))
                .andExpect(status().isNotFound())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void payForTelephone_BadRequest() throws Exception {
        AccountDto accountDto = createAccountDto();
        MvcResult res = mvcWithoutAuth.perform(MockMvcRequestBuilders
                        .put(partOfPath + "/pay-for-telephone/{fromId}/{money}/{comment}",
                                accountDto.getAccountId(), "1000000", "null"))
                .andExpect(status().isBadRequest())
                .andReturn();
        deleteAccountDto(accountDto.getAccountId());

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void payForTelephone_Ok() throws Exception {
        AccountDto accountDto = createAccountDto();
        MvcResult res = mvcWithoutAuth.perform(MockMvcRequestBuilders
                        .put(partOfPath + "/pay-for-telephone/{fromId}/{money}/{comment}",
                                accountDto.getAccountId(), "100", "ЖКХ"))
                .andExpect(status().isOk())
                .andReturn();
        deleteAccountDto(accountDto.getAccountId());

        assertTrue(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void payForTax_NotFound_From() throws Exception {

        MvcResult res = mvcWithoutAuth.perform(MockMvcRequestBuilders
                        .put(partOfPath + "/pay-for-tax/{fromId}/{money}/{comment}",
                                "1", "100", "null"))
                .andExpect(status().isNotFound())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void payForTax_BadRequest() throws Exception {
        AccountDto accountDto = createAccountDto();
        MvcResult res = mvcWithoutAuth.perform(MockMvcRequestBuilders
                        .put(partOfPath + "/pay-for-tax/{fromId}/{money}/{comment}",
                                accountDto.getAccountId(), "100000", "null"))
                .andExpect(status().isBadRequest())
                .andReturn();
        deleteAccountDto(accountDto.getAccountId());

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void payForTax_Ok() throws Exception {
        AccountDto accountDto = createAccountDto();
        MvcResult res = mvcWithoutAuth.perform(MockMvcRequestBuilders
                        .put(partOfPath + "/pay-for-tax/{fromId}/{money}/{comment}",
                                accountDto.getAccountId(), "100", "ЖКХ"))
                .andExpect(status().isOk())
                .andReturn();
        deleteAccountDto(accountDto.getAccountId());

        assertTrue(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void deleteById_Ok() throws Exception {
        AccountDto accountDto = createAccountDto();
        MvcResult res = mvcWithoutAuth.perform(MockMvcRequestBuilders
                        .delete(partOfPath + "/{id}", accountDto.getAccountId()))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(res.getResponse().getContentAsString().isBlank());
    }

    private void deleteAccountDto(Long id) {
        service.deleteById(id)
                .orElseThrow(() -> new RuntimeException("Не удалось удалить аккаунт"));
    }

    private AccountDto createAccountDto() {
        return service.addAccount()
                .map(dto -> service.update(dto.getAccountId(), 100L, PLUS, "PLUS").get())
                .orElseThrow(() -> new RuntimeException("Не удалось создать аккаунт"));
    }
}
