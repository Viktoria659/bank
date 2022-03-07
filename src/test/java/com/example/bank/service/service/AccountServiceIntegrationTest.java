package com.example.bank.service.service;

import com.example.bank.dto.AccountDto;
import com.example.bank.service.AccountService;
import com.example.bank.service.util.AbstractIntegrationTest;
import com.example.bank.util.error.NoEnoughMoneyException;
import com.example.bank.util.error.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

import static com.example.bank.service.util.TestUtil.toJson;
import static com.example.bank.util.Constant.MINUS;
import static com.example.bank.util.Constant.PLUS;
import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration
public class AccountServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    AccountService accountService;

    @Test
    public void addAccount() {
        Optional<AccountDto> res = accountService.addAccount();
        Optional<AccountDto> res_2 = accountService.addAccount();

        assertTrue(res.isPresent() && res_2.isPresent());

        accountService.update(res.get().getAccountId(), 1000L, PLUS, "PLUS");
        accountService.update(res.get().getAccountId(), 105L, MINUS, "MINUS");
        accountService.update(res.get().getAccountId(), 10L, PLUS, "PLUS");


        assertNotNull(res.get().getClient());
        assertNotNull(res_2.get().getClient());

        assertEquals(0L, res.get().getBalance());
        assertEquals(0L, res_2.get().getBalance());
    }

    @Test
    public void getAccountById_error() {
        Long id = 5L;

        Exception exception = assertThrows(NotFoundException.class, () -> accountService.getAccount(id));

        String expectedMessage = String.format("Object with id: %s does not exist", id);
        String actualMessage = exception.getMessage();

        assertNotNull(actualMessage);
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void getAccountById() {
        AccountDto accountDto = createAccountDto();
        Long accountId = accountDto.getAccountId();

        Optional<AccountDto> res = accountService.getAccount(accountId);

        assertTrue(res.isPresent());
        assertNotNull(res.get().getClient());

        assertEquals(toJson(toNullTime(accountDto)), toJson(toNullTime(res.get())));
    }

    @Test
    public void getAccounts() {
        Optional<List<AccountDto>> accounts = accountService.getAccounts();

        assertTrue(accounts.isPresent());
        assertTrue(accounts.get().size() > 0);
    }

    @Test
    public void update_Plus_error() {
        Long accountId = 5L;

        Exception exception = assertThrows(NotFoundException.class, () ->
                accountService.update(accountId, 500L, PLUS, null));

        String expectedMessage = String.format("Object with id: %s does not exist", accountId);
        String actualMessage = exception.getMessage();

        assertNotNull(actualMessage);
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void update_Minus_error() {
        AccountDto accountDto = createAccountDto();

        Exception exception = assertThrows(NoEnoughMoneyException.class, () ->
                accountService.update(accountDto.getAccountId(), 5000000000000000L, MINUS, null));

        String expectedMessage = "Not enough money on balance";
        String actualMessage = exception.getMessage();

        assertNotNull(actualMessage);
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void update_Plus() {
        AccountDto accountDto = createAccountDto();
        long money = 500L;

        Optional<AccountDto> res = accountService.update(accountDto.getAccountId(), money, PLUS, "PLUS");

        assertTrue(res.isPresent());
        assertEquals(res.get().getBalance(), accountDto.getBalance() + money);
    }

    @Test
    public void update_Minus() {
        AccountDto accountDto = createAccountDto();
        long money = 50L;
        accountService.update(accountDto.getAccountId(), money, PLUS, "PLUS");

        Optional<AccountDto> res = accountService.update(accountDto.getAccountId(), money, MINUS, "MINUS");

        assertTrue(res.isPresent());
        assertEquals(res.get().getBalance(), accountDto.getBalance());
    }

    @Test
    public void transferBetweenAccounts_error() {
        AccountDto accountFrom = createAccountDto();
        Optional<AccountDto> accountTo = accountService.addAccount();

        Exception exception = assertThrows(NoEnoughMoneyException.class, () ->
                accountService.transferBetweenAccounts(accountFrom.getAccountId(),
                        accountTo.get().getAccountId(), 500000000000L, null));

        String expectedMessage = "Not enough money on balance";
        String actualMessage = exception.getMessage();

        assertNotNull(actualMessage);
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void transferBetweenAccounts_nullComment() {
        long money = 500L;

        Optional<AccountDto> accountFrom = accountService.update(createAccountDto().getAccountId(),
                money, PLUS, null);
        Optional<AccountDto> accountTo = accountService.addAccount();

        assertTrue(accountFrom.isPresent());
        assertTrue(accountTo.isPresent());

        accountService.transferBetweenAccounts(accountFrom.get().getAccountId(),
                accountTo.get().getAccountId(), money, null);

        accountFrom = Optional.ofNullable(accountService.getAccount(accountFrom.get()
                .getAccountId()).get().toBuilder().modifiedDate(null).version(null).build());

        assertTrue(accountFrom.isPresent());
    }

    @Test
    public void transferBetweenAccounts() {
        long money = 500L;
        String comment = "comment";

        Optional<AccountDto> accountFrom = accountService.update(createAccountDto().getAccountId(),
                money, PLUS, comment);
        Optional<AccountDto> accountTo = accountService.addAccount();

        assertTrue(accountFrom.isPresent());
        assertTrue(accountTo.isPresent());

        accountService.transferBetweenAccounts(accountFrom.get().getAccountId(),
                accountTo.get().getAccountId(), money, comment);

        accountFrom = accountService.getAccount(accountFrom.get().getAccountId());

        assertTrue(accountFrom.isPresent());
    }

    @Test
    public void delete_error() {
        Long id = 1L;

        Exception exception = assertThrows(NotFoundException.class, () ->
                accountService.deleteById(id));

        String expectedMessage = String.format("Object with id: %s does not exist", id);
        String actualMessage = exception.getMessage();

        assertNotNull(actualMessage);
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void delete_validId() {
        Optional<AccountDto> account = accountService.addAccount();
        assertTrue(account.isPresent());

        Optional<AccountDto> res = accountService.deleteById(account.get().getAccountId());

        assertTrue(res.isPresent());
        res = Optional.ofNullable(toNullTime(res.get()));
        account = Optional.ofNullable(toNullTime(account.get()));
        assertTrue(account.isPresent());
        assertTrue(res.isPresent());
        assertEquals(toJson(account.get()), toJson(res.get()));
    }

    private AccountDto toNullTime(AccountDto dto) {
        return dto.toBuilder().modifiedDate(null).createdDate(null).version(null).build();
    }

    private AccountDto createAccountDto() {
        return accountService.getAccounts().stream().findAny().get().iterator().next();
    }
}
