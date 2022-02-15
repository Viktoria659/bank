package com.example.bank.controller;

import com.example.bank.dto.AccountDto;
import com.example.bank.service.AccountService;
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

import static com.example.bank.util.Constants.MINUS;
import static com.example.bank.util.Constants.PLUS;

@Slf4j
@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "AccountController")
public class AccountController {
    AccountService service;

    @Operation(summary = "Добавление нового счёта")
    @PostMapping("/add-account/{username}")
    public ResponseEntity<AccountDto> addAccount(@Parameter(description = "Username пользователя")
                                                 @PathVariable final String username) {
        log.info("Add account");
        return service.addAccount(username)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Поиск счёта по id")
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> findById(@Parameter(description = "Идентификатор счёта")
                                               @PathVariable final Long id) {
        log.info("Search account by id");
        return service.getAccount(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Поиск всех счетов")
    @GetMapping("/get-accounts")
    public ResponseEntity<Set<AccountDto>> getAccounts() {
        log.info("Search all accounts");
        return service.getAccounts()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Поиск всех счетов клиента")
    @GetMapping("/get-accounts/{username}")
    public ResponseEntity<Set<AccountDto>> getAccountsByUserUsername(@Parameter(description = "Username пользователя")
                                                                     @PathVariable final String username) {
        log.info("Search all accounts");
        return service.getAccountsByUserUsername(username)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Пополнение счёта")
    @PutMapping("/refill-account-balance/{id}/{money}")
    public ResponseEntity<AccountDto> updateRefillBalance(@Parameter(description = "Идентификатор счёта")
                                                          @PathVariable final Long id,
                                                          @PathVariable final Long money) {
        log.info("Update account");
        return service.update(id, money, PLUS, "Пополнение счёта")
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Снятие средств со счёта")
    @PutMapping("/withdraw-account-balance/{id}/{money}")
    public ResponseEntity<AccountDto> updateWithdrawBalance(@Parameter(description = "Идентификатор счёта")
                                                            @PathVariable final Long id,
                                                            @Parameter(description = "Сумма снятия")
                                                            @PathVariable final Long money) {
        log.info("Update account");
        return service.update(id, money, MINUS, "Снятие средств со счёта")
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Перевод средств между счетами")
    @PutMapping("/transfer-between-accounts/{fromId}/{toId}/{money}/{comment}")
    public ResponseEntity<?> transferBetweenAccounts(@Parameter(description = "Идентификатор счёта снятия")
                                                     @PathVariable final Long fromId,
                                                     @Parameter(description = "Идентификатор счёта зачисления")
                                                     @PathVariable final Long toId,
                                                     @Parameter(description = "Сумма перевода")
                                                     @PathVariable final Long money,
                                                     @Parameter(description = "Цель переводаа")
                                                     @PathVariable String comment) {
        log.info("Transfer between accounts");
        service.transferBetweenAccounts(fromId, toId, money, comment);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Перевод средств за ЖКХ")
    @PutMapping("/pay-for-communal-services/{fromId}/{money}/{comment}")
    public ResponseEntity<?> payForCommunalServices(@Parameter(description = "Идентификатор счёта снятия")
                                                    @PathVariable final Long fromId,
                                                    @Parameter(description = "Сумма перевода")
                                                    @PathVariable final Long money,
                                                    @Parameter(description = "Цель переводаа")
                                                    @PathVariable String comment) {
        log.info("Transfer between accounts");
        service.transferBetweenAccountAndJkx(fromId, money, comment);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Перевод средств за телефон")
    @PutMapping("/pay-for-telephone/{fromId}/{money}/{comment}")
    public ResponseEntity<?> payForTelephone(@Parameter(description = "Идентификатор счёта снятия")
                                             @PathVariable final Long fromId,
                                             @Parameter(description = "Сумма перевода")
                                             @PathVariable final Long money,
                                             @Parameter(description = "Цель переводаа")
                                             @PathVariable String comment) {
        log.info("Transfer between accounts");
        service.transferBetweenAccountAndTelephone(fromId, money, comment);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Оплата налогов")
    @PutMapping("/pay-for-tax/{fromId}/{money}/{comment}")
    public ResponseEntity<?> payForTax(@Parameter(description = "Идентификатор счёта снятия")
                                       @PathVariable final Long fromId,
                                       @Parameter(description = "Сумма перевода")
                                       @PathVariable final Long money,
                                       @Parameter(description = "Цель переводаа")
                                       @PathVariable String comment) {
        log.info("Transfer between accounts");
        service.transferBetweenAccountAndTax(fromId, money, comment);
        return ResponseEntity.ok().build();
    }
}



