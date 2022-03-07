package com.example.bank.service;

import com.example.bank.dto.AccountDto;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    Optional<AccountDto> addAccount(String username);

    Optional<AccountDto> getAccount(Long id);

    Optional<List<AccountDto>> getAccounts();

    Optional<List<AccountDto>> getAccountsByUserUsername(String username);

    Optional<List<AccountDto>> getAccountsByCurrentUser();

    Optional<AccountDto> update(Long accountId, Long money, byte operand, String comment);

    void transferBetweenAccounts(Long fromId, Long toId, Long money, String comment);

    void transferBetweenAccountAndJkx(Long fromId, Long money, String comment);

    void transferBetweenAccountAndTelephone(Long fromId, Long money, String comment);

    void transferBetweenAccountAndTax(Long fromId, Long money, String comment);

    Optional<AccountDto> deleteById(Long id);
}
