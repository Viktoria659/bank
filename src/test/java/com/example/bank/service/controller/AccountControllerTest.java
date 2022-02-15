package com.example.bank.service.controller;

import com.example.bank.service.util.AbstractControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountControllerTest extends AbstractControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mvc;
    private static final String partOfPath = "/account";

    @BeforeEach
    public void init() {
        mvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    public void addAccount_Ok() throws Exception {

        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .post(partOfPath + "/add-account/{username}", "username"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.client.id").exists())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void addAccount_NotFound() throws Exception {

        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .post(partOfPath + "/add-account/{username}", "123"))
                .andExpect(status().isNotFound())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void findById_Ok() throws Exception {

        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .get(partOfPath + "/{id}", "860"))
                .andExpect(status().isOk())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void findById_NotFound() throws Exception {

        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .get(partOfPath + "/{id}", "1"))
                .andExpect(status().isNotFound())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void getAccounts_Ok() throws Exception {

        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .get(partOfPath + "/get-accounts"))
                .andExpect(status().isOk())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void getAccountsByUserUsername_Ok() throws Exception {

        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .get(partOfPath + "/get-accounts/{username}", "username"))
                .andExpect(status().isOk())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void getAccountsByUserUsername_NotFound() throws Exception {

        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .get(partOfPath + "/get-accounts/{username}", "1"))
                .andExpect(status().isNotFound())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void updateRefillBalance_Ok() throws Exception {

        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .put(partOfPath + "/refill-account-balance/{id}/{money}", "860", "5000"))
                .andExpect(status().isOk())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void updateRefillBalance_NotFound() throws Exception {

        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .put(partOfPath + "/refill-account-balance/{id}/{money}", "1", "50"))
                .andExpect(status().isNotFound())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void updateWithdrawBalance_Ok() throws Exception {

        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .put(partOfPath + "/withdraw-account-balance/{id}/{money}", "860", "500"))
                .andExpect(status().isOk())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void updateWithdrawBalance_NotFound() throws Exception {

        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .put(partOfPath + "/withdraw-account-balance/{id}/{money}", "1", "50"))
                .andExpect(status().isNotFound())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void updateWithdrawBalance_BadRequest() throws Exception {

        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .put(partOfPath + "/withdraw-account-balance/{id}/{money}", "860", "50000"))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void transferBetweenAccounts_NotFound_From() throws Exception {

        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .put(partOfPath + "/transfer-between-accounts/{fromId}/{toId}/{money}/{comment}",
                                "1", "851", "100", "null"))
                .andExpect(status().isNotFound())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void transferBetweenAccounts_NotFound_To() throws Exception {

        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .put(partOfPath + "/transfer-between-accounts/{fromId}/{toId}/{money}/{comment}",
                                "860", "1", "100", "null"))
                .andExpect(status().isNotFound())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void transferBetweenAccounts_BadRequest() throws Exception {

        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .put(partOfPath + "/transfer-between-accounts/{fromId}/{toId}/{money}/{comment}",
                                "860", "851", "1000000000", "null"))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void transferBetweenAccounts_Ok() throws Exception {

        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .put(partOfPath + "/transfer-between-accounts/{fromId}/{toId}/{money}/{comment}",
                                "860", "861", "100", "newComment"))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void payForCommunalServices_NotFound_From() throws Exception {

        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .put(partOfPath + "/pay-for-communal-services/{fromId}/{money}/{comment}",
                                "1", "100", "null"))
                .andExpect(status().isNotFound())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void payForCommunalServices_BadRequest() throws Exception {

        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .put(partOfPath + "/pay-for-communal-services/{fromId}/{money}/{comment}",
                                "861", "100", "null"))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void payForCommunalServices_Ok() throws Exception {

        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .put(partOfPath + "/pay-for-communal-services/{fromId}/{money}/{comment}",
                                "860", "100", "ЖКХ"))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void payForTelephone_NotFound_From() throws Exception {

        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .put(partOfPath + "/pay-for-telephone/{fromId}/{money}/{comment}",
                                "1", "100", "null"))
                .andExpect(status().isNotFound())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void payForTelephone_BadRequest() throws Exception {

        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .put(partOfPath + "/pay-for-telephone/{fromId}/{money}/{comment}",
                                "861", "100", "null"))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void payForTelephone_Ok() throws Exception {

        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .put(partOfPath + "/pay-for-telephone/{fromId}/{money}/{comment}",
                                "860", "100", "ЖКХ"))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void payForTax_NotFound_From() throws Exception {

        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .put(partOfPath + "/pay-for-tax/{fromId}/{money}/{comment}",
                                "1", "100", "null"))
                .andExpect(status().isNotFound())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void payForTax_BadRequest() throws Exception {

        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .put(partOfPath + "/pay-for-tax/{fromId}/{money}/{comment}",
                                "861", "100", "null"))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void payForTax_Ok() throws Exception {

        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .put(partOfPath + "/pay-for-tax/{fromId}/{money}/{comment}",
                                "860", "100", "ЖКХ"))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(res.getResponse().getContentAsString().isBlank());
    }
}
