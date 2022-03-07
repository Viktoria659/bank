package com.example.bank.service.service;

import com.example.bank.dto.AccountDto;
import com.example.bank.dto.ClientDto;
import com.example.bank.dto.UserDto;
import com.example.bank.entities.AccountEntity;
import com.example.bank.entities.ClientEntity;
import com.example.bank.repo.AccountRepo;
import com.example.bank.service.AccountServiceImpl;
import com.example.bank.service.ClientService;
import com.example.bank.util.JwtUtil;
import com.example.bank.util.error.NoEnoughMoneyException;
import com.example.bank.util.error.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Optional;

import static com.example.bank.service.util.TestUtil.toJson;
import static com.example.bank.util.Constant.MINUS;
import static com.example.bank.util.Constant.PLUS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

public class AccountServiceTest {

    @InjectMocks
    @Spy
    AccountServiceImpl accountService;

    @Mock
    AccountRepo repo;
    @Mock
    ClientService clientService;
    @Mock
    JwtUtil jwtUtil;

    private static final long accountId = 5L;
    private static final long money = 500L;
    private static final long balance = 123L;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addAccount() {
        String username = "username";

        doReturn(username).when(jwtUtil).getCurrentUsername();
        doReturn(Optional.of(createClientDto())).when(clientService).getClientByUsername(username);
        doReturn(createAccountEntity()).when(repo).save(any(AccountEntity.class));

        Optional<AccountDto> res = accountService.addAccount();

        assertTrue(res.isPresent());
        assertNotNull(res.get().getClient());

        assertEquals(toJson(createAccountDto()), toJson(res.get()));
    }

    @Test
    public void addAccount_error() {
        String username = "123";

        doReturn(username).when(jwtUtil).getCurrentUsername();
        doThrow(new NotFoundException(username)).when(clientService).getClientByUsername(username);

        Exception exception = assertThrows(NotFoundException.class, () -> accountService.addAccount());

        String expectedMessage = String.format("Object with username: %s does not exist", username);
        String actualMessage = exception.getMessage();

        assertNotNull(actualMessage);
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void update_Plus_error() {

        doThrow(new NotFoundException(accountId)).when(accountService).getAccount(accountId);

        Exception exception = assertThrows(NotFoundException.class, () ->
                accountService.update(accountId, money, PLUS, null));

        String expectedMessage = String.format("Object with id: %s does not exist", accountId);
        String actualMessage = exception.getMessage();

        assertNotNull(actualMessage);
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void update_Minus_error() {
        doReturn(Optional.of(createAccountDto())).when(accountService).getAccount(accountId);
        doThrow(new NoEnoughMoneyException())
                .when(accountService).updateBalance(money, createAccountDto(), MINUS, null);

        Exception exception = assertThrows(NoEnoughMoneyException.class, () ->
                accountService.update(accountId, money, MINUS, null));

        String expectedMessage = "Not enough money on balance";
        String actualMessage = exception.getMessage();

        assertNotNull(actualMessage);
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    public void update_Plus() {
        doReturn(Optional.of(createAccountDto())).when(accountService).getAccount(accountId);
        AccountEntity accountEntity = createAccountEntity();
        accountEntity.setBalance(balance + money);
        doReturn(accountEntity).when(repo).save(any(AccountEntity.class));

        Optional<AccountDto> res = accountService.update(accountId, money, PLUS, "PLUS");

        assertTrue(res.isPresent());
        assertEquals(res.get().getBalance(), balance + money);
    }

    @Test
    public void update_Minus() {
        long money = 50L;
        doReturn(Optional.of(createAccountDto())).when(accountService).getAccount(accountId);
        AccountEntity accountEntity = createAccountEntity();
        accountEntity.setBalance(balance - money);
        doReturn(accountEntity).when(repo).save(any(AccountEntity.class));

        Optional<AccountDto> res = accountService.update(accountId, money, MINUS, "MINUS");

        assertTrue(res.isPresent());
        assertEquals(res.get().getBalance(), balance - money);
    }

    public AccountDto createAccountDto() {
        return AccountDto.builder()
                .accountId(accountId)
                .balance(balance)
                .client(ClientDto.builder().build())
                .build();
    }

    private AccountEntity createAccountEntity() {
        AccountEntity entity = new AccountEntity();

        entity.setAccountId(accountId);
        entity.setBalance(balance);
        entity.setClient(new ClientEntity());

        return entity;
    }

    private ClientDto createClientDto() {
        return ClientDto.builder()
                .id(accountId)
                .user(UserDto.builder().build())
                .build();
    }
}
