package com.example.bank.controller;

import com.example.bank.dto.ClientDto;
import com.example.bank.dto.UserDto;
import com.example.bank.service.UserService;
import com.example.bank.util.error.NullRequiredFieldException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;

@Controller
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "PageController")
public class PageController {

    UserService service;

    @GetMapping
    public String main() {
        return "redirect:/accounts";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile.html";
    }

    @GetMapping("/accounts")
    public String accounts() {
        return "accounts.html";
    }

    @GetMapping("/history")
    public String history() {
        return "history.html";
    }

    @GetMapping("/update-balance")
    public String updateBalance() {
        return "update-balance.html";
    }

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @GetMapping("/payment")
    public String payment() {
        return "payment.html";
    }

    @GetMapping("/payment-between")
    public String paymentBetween() {
        return "payment-between.html";
    }

    @PostMapping(value = "/registration", produces = "text/html")
    public String addUser(HttpServletResponse resp, @ModelAttribute UserDto userDto) {
        Optional<UserDto> user = service.getUser(userDto.getUsername());
        if (user.isPresent()) {
            return "redirect:/login";
        }
        userDto = validate(userDto);
        log.info("Add user");
        service.addUserBase(userDto);
        return "redirect:/login";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration.html";
    }

    private UserDto validate(UserDto dto) {
        log.info("Started validating userDto");
        if (dto.getUsername() == null || dto.getPassword() == null) {
            throw new NullRequiredFieldException("User");
        }
        if (dto.getClient() == null) {
            return dto.toBuilder()
                    .client(ClientDto.builder()
                            .firstname(String.valueOf(UUID.randomUUID()))
                            .surname(String.valueOf(UUID.randomUUID()))
                            .build())
                    .build();
        }
        return dto;
    }
}
