package com.example.bank.service.controller;

import com.example.bank.dto.ClientDto;
import com.example.bank.dto.RoleDto;
import com.example.bank.dto.UserDto;
import com.example.bank.service.UserService;
import com.example.bank.service.util.AbstractControllerTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends AbstractControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mvc;

    @Autowired
    UserService service;

    private static final String partOfPath = "/user";

    @BeforeEach
    public void init() {
        mvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    public void addUser_Ok() throws Exception {
        String username = String.valueOf(UUID.randomUUID());

        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .post(partOfPath + "/add-user")
                        .content(asJsonString(createUserDto(username)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.client.id").exists())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void addUser_BadRequest() throws Exception {

        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .post(partOfPath + "/add-user")
                        .content(asJsonString(createUserDto(null)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void updateUser_Ok() throws Exception {

        String username = String.valueOf(UUID.randomUUID());
        UserDto newUser = addUserDto(username);

        UserDto userDto = createUserDto(username).toBuilder()
                .id(newUser.getId())
                .role(RoleDto.builder().roleId(1L).build()).build();

        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .put(partOfPath + "/update-user")
                        .content(asJsonString(userDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.client.id").exists())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void updateUser_BadRequest() throws Exception {

        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .put(partOfPath + "/update-user")
                        .content(asJsonString(createUserDto(null)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void getUsers() throws Exception {

        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .get(partOfPath + "/get-users"))
                .andExpect(status().isOk())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void getUser_Ok() throws Exception {

        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .get(partOfPath + "/{username}", "username"))
                .andExpect(status().isOk())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    @Test
    public void getCurrentUser_Ok() throws Exception {

        MvcResult res = mvc.perform(MockMvcRequestBuilders
                        .get(partOfPath))
                .andExpect(status().isOk())
                .andReturn();

        assertFalse(res.getResponse().getContentAsString().isBlank());
    }

    private UserDto addUserDto(String username) {
        return service.addUserBase(createUserDto(username))
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

    private static String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
