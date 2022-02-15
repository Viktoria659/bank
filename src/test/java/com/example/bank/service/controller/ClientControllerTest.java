package com.example.bank.service.controller;

import com.example.bank.service.util.AbstractControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ClientControllerTest extends AbstractControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mvc;
    private static final String partOfPath = "/client";


    @BeforeEach
    public void init() {
        mvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
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
    public void findById_Ok() throws Exception {

        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .get(partOfPath + "/{id}", "1388"))
                .andExpect(status().isOk())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void findByUsername_Ok() throws Exception {

        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .get(partOfPath + "/username/{username}", "username"))
                .andExpect(status().isOk())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void findByUsername_NotFound() throws Exception {

        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .get(partOfPath + "/username/{username}", "123"))
                .andExpect(status().isNotFound())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void getClients() throws Exception {

        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .get(partOfPath + "/get-clients"))
                .andExpect(status().isOk())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }
}
