package com.example.bank.service.controller;

import com.example.bank.dto.ClientDto;
import com.example.bank.dto.UserDto;
import com.example.bank.service.util.AbstractIntegrationTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ClientControllerTest extends AbstractIntegrationTest {

    private static final String partOfPath = "/client";

    @Test
    public void findById_NotFound() throws Exception {

        MvcResult res = mvcWithoutAuth.perform(MockMvcRequestBuilders
                        .get(partOfPath + "/{id}", "1"))
                .andExpect(status().isNotFound())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void findById_Ok() throws Exception {
        String username = String.valueOf(UUID.randomUUID());
        UserDto user = addUserDto(username);

        MvcResult res = mvcWithoutAuth.perform(MockMvcRequestBuilders
                        .get(partOfPath + "/{id}", user.getId()))
                .andExpect(status().isOk())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void findByUsername_Ok() throws Exception {
        String username = String.valueOf(UUID.randomUUID());
        UserDto user = addUserDto(username);

        MvcResult res = mvcWithoutAuth.perform(MockMvcRequestBuilders
                        .get(partOfPath + "/username/{username}", user.getUsername()))
                .andExpect(status().isOk())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void findByUsername_NotFound() throws Exception {

        MvcResult res = mvcWithoutAuth.perform(MockMvcRequestBuilders
                        .get(partOfPath + "/username/{username}", "123"))
                .andExpect(status().isNotFound())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void getClients() throws Exception {

        MvcResult res = mvcWithoutAuth.perform(MockMvcRequestBuilders
                        .get(partOfPath + "/get-clients"))
                .andExpect(status().isOk())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    private UserDto addUserDto(String username) {
        return userService.addUserBase(createUserDto(username))
                .orElseThrow(() -> new RuntimeException("Не удалось создать пользователя"));
    }

    private UserDto createUserDto(String username) {
        return UserDto.builder()
                .username(username)
                .password("123")
                .active(true)
                .client(ClientDto.builder()
                        .firstname("Василий")
                        .surname("Белый")
                        .build())
                .build();
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
