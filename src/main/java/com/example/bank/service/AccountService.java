package com.example.bank.service;

import com.example.bank.dto.AccountDto;

import java.util.Optional;
import java.util.Set;

public interface AccountService {
    Optional<AccountDto> addAccount(String username);

    Optional<AccountDto> getAccount(Long id);

    Optional<Set<AccountDto>> getAccounts();

    Optional<Set<AccountDto>> getAccountsByUserUsername(String username);

    Optional<AccountDto> update(Long accountId, Long money, byte operand, String comment);

    void transferBetweenAccounts(Long fromId, Long toId, Long money, String comment);

    void transferBetweenAccountAndJkx(Long fromId, Long money, String comment);

    void transferBetweenAccountAndTelephone(Long fromId, Long money, String comment);

    void transferBetweenAccountAndTax(Long fromId, Long money, String comment);

    Optional<AccountDto> deleteById(Long id);
}
