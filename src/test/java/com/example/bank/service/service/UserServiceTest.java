package com.example.bank.service.service;

import com.example.bank.dto.ClientDto;
import com.example.bank.dto.RoleDto;
import com.example.bank.dto.UserDto;
import com.example.bank.service.UserService;
import com.example.bank.service.util.AbstractIntegrationTest;
import com.example.bank.util.error.NotFoundException;
import com.example.bank.util.error.NullRequiredFieldException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.bank.service.util.TestUtil.toJson;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest extends AbstractIntegrationTest {

    @Autowired
    UserService userService;

    @Test
    public void addUserBase_withEmptyUser_error() {
        UserDto userDto = UserDto.builder().build();

        Exception exception = assertThrows(NullRequiredFieldException.class, () -> userService.addUserBase(userDto));

        String expectedMessage = String.format("%s's required field is null", "User");
        String actualMessage = exception.getMessage();

        assertNotNull(actualMessage);
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void addUserBase_withEmptyPasswordAndEmptyClient_error() {
        UserDto userDto = UserDto.builder().username("username-2").build();

        Exception exception = assertThrows(NullRequiredFieldException.class, () -> userService.addUserBase(userDto));

        String expectedMessage = String.format("%s's required field is null", "User");
        String actualMessage = exception.getMessage();

        assertNotNull(actualMessage);
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void addUserBase_withEmptyClient_error() {
        UserDto userDto = createUserDto(null).toBuilder().client(null).build();

        Exception exception = assertThrows(NullRequiredFieldException.class, () -> userService.addUserBase(userDto));

        String expectedMessage = String.format("%s's required field is null", "User");
        String actualMessage = exception.getMessage();

        assertNotNull(actualMessage);
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void addUserBase_withEmptyClientSurname_error() {
        UserDto userDto = createUserDto(null).toBuilder()
                .client(ClientDto.builder()
                        .surname(null)
                        .build())
                .build();

        Exception exception = assertThrows(NullRequiredFieldException.class, () -> userService.addUserBase(userDto));

        String expectedMessage = String.format("%s's required field is null", "User");
        String actualMessage = exception.getMessage();

        assertNotNull(actualMessage);
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void addUserBase_withEmptyClientFirstname_error() {
        UserDto userDto = createUserDto(null).toBuilder()
                .client(ClientDto.builder()
                        .firstname(null)
                        .build())
                .build();

        Exception exception = assertThrows(NullRequiredFieldException.class, () -> userService.addUserBase(userDto));

        String expectedMessage = String.format("%s's required field is null", "User");
        String actualMessage = exception.getMessage();

        assertNotNull(actualMessage);
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void addUserBase_allField_successfulSave() {
        UserDto userDto = createUserDto(null).toBuilder().username("addUser").build();

        Optional<UserDto> res = createActualUserDto(userService.addUserBase(userDto));

        assertEquals(toJson(createActualUserDto(Optional.of(userDto)).get()), toJson(res.get()));
    }

    @Test
    public void getUser_error() {
        String username = "1234";

        Exception exception = assertThrows(NotFoundException.class, () -> userService.getUser(username));

        String expectedMessage = String.format("Object with username: %s does not exist", username);
        String actualMessage = exception.getMessage();
        
        assertNotNull(actualMessage);
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void getUser_successfulGetUser() {
        String username = getUsername();
        Optional<UserDto> res = userService.getUser(username);

        if (res.isPresent()) res = toClientAccountsNull(res.get());

        UserDto userExpected = createWithoutAccounts(username);

        assertTrue(res.isPresent());
        assertEquals(toJson(userExpected), toJson(res.get()));
    }

    @Test
    public void getUsers_successfulGetUsers() {
        Optional<List<UserDto>> users = userService.getUsers();

        assertTrue(users.isPresent());
        assertTrue(users.get().size() > 0);
    }

    @Test
    public void updateUser_withEmptyUserAndEmptyUsername_error() {
        Exception exception = assertThrows(NullRequiredFieldException.class, () ->
                userService.update(UserDto.builder().build())
        );

        String expectedMessage = String.format("%s's required field is null", "User");
        String actualMessage = exception.getMessage();

        assertNotNull(actualMessage);
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void updateUser_withEmptyPasswordAndEmptyClient_error() {
        UserDto userDto = UserDto.builder().username("username-2").build();

        Exception exception = assertThrows(NullRequiredFieldException.class, () -> userService.update(userDto));

        String expectedMessage = String.format("%s's required field is null", "User");
        String actualMessage = exception.getMessage();

        assertNotNull(actualMessage);
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void updateUser_withEmptyClient_error() {
        UserDto userDto = createUserDto(null).toBuilder().client(null).build();

        Exception exception = assertThrows(NullRequiredFieldException.class, () -> userService.update(userDto));

        String expectedMessage = String.format("%s's required field is null", "User");
        String actualMessage = exception.getMessage();

        assertNotNull(actualMessage);
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void updateUser_withEmptyClientSurname_error() {
        UserDto userDto = createUserDto(null).toBuilder().
                client(ClientDto.builder()
                        .surname(null)
                        .build())
                .build();

        Exception exception = assertThrows(NullRequiredFieldException.class, () -> userService.update(userDto));

        String expectedMessage = String.format("%s's required field is null", "User");
        String actualMessage = exception.getMessage();

        assertNotNull(actualMessage);
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void updateUser_withEmptyClientFirstname_error() {
        UserDto userDto = createUserDto(null).toBuilder().
                client(ClientDto.builder()
                        .firstname(null)
                        .build())
                .build();

        Exception exception = assertThrows(NullRequiredFieldException.class, () -> userService.update(userDto));


        String expectedMessage = String.format("%s's required field is null", "User");
        String actualMessage = exception.getMessage();

        assertNotNull(actualMessage);
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void updateUser_withInvalidId_error() {
        Long id = 1L;
        UserDto userDto = createUserDto(id).toBuilder()
                .role(RoleDto.builder()
                        .roleId(id)
                        .build())
                .build();

        Exception exception = assertThrows(NotFoundException.class, () -> userService.update(userDto));


        String expectedMessage = String.format("Object with id: %s does not exist", id);
        String actualMessage = exception.getMessage();

        assertNotNull(actualMessage);
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void updateUser_successfulUpdateUser() {
        Long userId = getUserId();
        UserDto userDto = createUserDto(userId).toBuilder().role(RoleDto.builder().roleId(1L).build()).build();

        UserDto res = userService.update(userDto).get().toBuilder()
                .role(RoleDto.builder().roleId(1L).build())
                .build();

        assertEquals(toJson(userDto), toJson(res));
    }

    public String getUsername() {
        return userService.getUsers().stream().findAny().get().iterator().next().getUsername();
    }

    public Long getUserId() {
        return userService.getUsers().stream().findAny().get().iterator().next().getId();
    }

    private UserDto createWithoutAccounts(String username) {
        return UserDto.builder()
                .id(getUserId())
                .username(username)
                .password("password")
                .active(true)
                .role(RoleDto.builder()
                        .roleId(1L)
                        .value("ROLE_USER")
                        .build())
                .client(ClientDto.builder()
                        .id(getUserId())
                        .firstname("firstname")
                        .surname("surname")
                        .build())
                .build();
    }

    private Optional<UserDto> toClientAccountsNull(UserDto res) {
        return Optional.of(res.toBuilder()
                .client(res.getClient().toBuilder()
                        .build())
                .build());
    }

    private UserDto createUserDto(Long clientId) {
        return UserDto.builder()
                .id(clientId)
                .username("user")
                .password("123")
                .active(true)
                .client(ClientDto.builder()
                        .id(clientId)
                        .firstname("Василий")
                        .surname("Белый")
                        .build())
                .build();
    }

    private Optional<UserDto> createActualUserDto(Optional<UserDto> userDto) {
        return userDto
                .map(e -> e.toBuilder()
                        .id(null)
                        .client(e.getClient().toBuilder().id(null).build())
                        .role(RoleDto.builder().value(null).build())
                        .build()
                );
    }
}