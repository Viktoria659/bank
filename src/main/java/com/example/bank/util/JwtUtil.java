package com.example.bank.util;

import com.example.bank.dto.UserDto;
import com.example.bank.util.error.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class JwtUtil {

    public String getCurrentUsername() {
        return getCurrentUser()
                .map(o -> {
                    String username = o.getUsername();
                    log.info("Current user with username: {}", username);
                    return username;
                })
                .orElseThrow(() -> new NotFoundException("current user"));
    }

    public Optional<UserDto> getCurrentUser() {
        return Optional.ofNullable((Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(o -> {
                    UserDto user = (UserDto) o;
                    log.info("Current user with username: {}", user.getUsername());
                    return user;
                }))
                .orElseThrow(() -> new NotFoundException("current user")));
    }
}
