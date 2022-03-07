package com.example.bank.util;

import com.example.bank.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UtilPassword {
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDto setPasswordEncoder(UserDto userDto) {
        log.info("Encoding of password");
        return userDto.toBuilder()
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build();
    }
}
