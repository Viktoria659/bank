package com.example.bank.service;

import com.example.bank.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    Optional<UserDto> update(UserDto userDto);

    Optional<UserDto> getUser(String username);

    Optional<UserDto> getUser();

    Optional<List<UserDto>> getUsers();

    Optional<UserDto> addUserBase(UserDto dto);
}
