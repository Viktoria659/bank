package com.example.bank.controller;

import com.example.bank.dto.UserDto;
import com.example.bank.service.UserService;
import com.example.bank.util.error.NullRequiredFieldException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "UserController")
public class UserController {
    UserService service;

    @Operation(summary = "Добавление нового пользователя")
    @PostMapping("/add-user")
    public ResponseEntity<UserDto> addUserBase(@Parameter(description = "Параметры пользователя")
                                               @RequestBody final UserDto userDto) {
        validate(userDto);
        log.info("Add user");
        return service.addUserBase(userDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Поиск пользователя по username")
    @GetMapping("/{username}")
    public ResponseEntity<UserDto> findByUsername(@Parameter(description = "Username пользователя", example = "username")
                                                  @PathVariable final String username) {
        log.info("Search user by username");
        return service.getUser(username)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Поиск всех пользователей")
    @GetMapping("/get-users")
    public ResponseEntity<Set<UserDto>> getUsers() {
        log.info("Search all users");
        return service.getUsers()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Изменение пользователя")
    @PutMapping("/update-user")
    public ResponseEntity<UserDto> update(@Parameter(description = "Параметры пользователя")
                                          @RequestBody final UserDto userDto) {
        validate(userDto);
        log.info("Update user");
        return service.update(userDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private void validate(UserDto dto) {
        log.info("Started validating userDto");
        if (dto.getUsername() == null || dto.getPassword() == null ||
                dto.getClient() == null ||
                dto.getClient().getSurname() == null || dto.getClient().getFirstname() == null) {
            throw new NullRequiredFieldException("User");
        }
    }
}
