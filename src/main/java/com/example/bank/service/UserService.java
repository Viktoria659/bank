package com.example.bank.service;

import com.example.bank.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;
import java.util.Set;

public interface UserService extends UserDetailsService {
    Optional<UserDto> update(UserDto userDto);

    Optional<UserDto> getUser(String username);

    Optional<Set<UserDto>> getUsers();

    Optional<UserDto> addUserBase(UserDto dto);
}
