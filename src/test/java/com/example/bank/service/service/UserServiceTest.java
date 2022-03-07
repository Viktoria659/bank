package com.example.bank.service.service;

import com.example.bank.dto.ClientDto;
import com.example.bank.dto.RoleDto;
import com.example.bank.dto.UserDto;
import com.example.bank.service.util.AbstractIntegrationTest;
import com.example.bank.util.error.NotFoundException;
import com.example.bank.util.error.NullRequiredFieldException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.bank.service.util.TestUtil.toJson;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest extends AbstractIntegrationTest {

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
        String username = String.valueOf(UUID.randomUUID());

        UserDto userDto = createUserDto(null).toBuilder().username(username).build();

        UserDto res = createActualUserDto(userService.addUserBase(userDto).get());

        UserDto userExpected = createActualUserDto(userDto);
        assertEquals(toJson(userExpected), toJson(res));
    }

    @Test
    public void getUser_successfulGetUser() {
        UserDto userExpected = createWithoutAccountsAndPassword(getUser());
        String username = userExpected.getUsername();

        Optional<UserDto> res = userService.getUser(username);

        if (res.isPresent()) res = toPasswordAndClientAccountsNull(res.get());

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
        Optional<UserDto> newUser = userService.addUserBase(createUserDto(null));
        assertTrue(newUser.isPresent());

        String username = String.valueOf(UUID.randomUUID());
        UserDto userDto = createUserDto(newUser.get().getId()).toBuilder()
                .id(newUser.get().getId())
                .username(username)
                .role(RoleDto.builder().roleId(1L).build()).build();

        Optional<UserDto> res = toPasswordAndClientAccountsNull(userService.update(userDto).get().toBuilder()
                .role(RoleDto.builder().roleId(1L).build())
                .build());

        assertTrue(res.isPresent());
        assertEquals(toJson(toPasswordAndClientAccountsNull(userDto).get()), toJson(res.get()));
    }

    private UserDto getUser() {
        return userService.getUsers().stream().findAny().get().iterator().next();
    }

    private UserDto createWithoutAccountsAndPassword(UserDto userDto) {
        userDto.getClient().getAccounts().clear();
        return userDto.toBuilder().password(null).build();
    }

    private Optional<UserDto> toPasswordAndClientAccountsNull(UserDto res) {
        return Optional.of(res.toBuilder()
                .password(null)
                .client(res.getClient().toBuilder()
                        .build())
                .build());
    }

    private UserDto createUserDto(Long clientId) {
        String username = String.valueOf(UUID.randomUUID());

        return UserDto.builder()
                .id(clientId)
                .username(username)
                .password("123")
                .active(true)
                .client(ClientDto.builder()
                        .id(clientId)
                        .firstname("Василий")
                        .surname("Белый")
                        .build())
                .build();
    }

    private UserDto createActualUserDto(UserDto userDto) {
        return userDto.toBuilder()
                .id(null)
                .password(null)
                .client(userDto.getClient().toBuilder().id(null).build())
                .role(RoleDto.builder().value(null).build())
                .build();
    }
}