package com.example.bank.service;

import com.example.bank.dto.AccountDto;
import com.example.bank.dto.ClientDto;
import com.example.bank.mapper.AccountMapper;
import com.example.bank.repo.AccountRepo;
import com.example.bank.util.error.NoEnoughMoneyException;
import com.example.bank.util.error.NotFoundException;
import com.example.bank.util.error.NotSaveException;
import com.google.common.annotations.VisibleForTesting;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import static com.example.bank.util.Constants.MINUS;
import static com.example.bank.util.Constants.PLUS;

/**
 * @author Filippova_Viktoria
 */

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountServiceImpl implements AccountService {

//    @PersistenceContext
//    EntityManager entityManager;
    AccountMapper mapper = Mappers.getMapper(AccountMapper.class);
    AccountRepo repo;
    ClientService clientService;

    @Value("${jkx.address:}")
    String jkx;
    @Value("${telephone.address:}")
    String telephone;
    @Value("${tax.address:}")
    String tax;

    public Optional<AccountDto> addAccount(String username) {
        log.info("Start add account to client with username: {}", username);
        return clientService.getClientByUsername(username)
                .map(clientDto -> {
                    AccountDto account = createAccount(clientDto);
                    clientDto.getAccounts().add(account);
                    Optional<AccountDto> save = save(account);
                    return save.get();
                });
    }

    private AccountDto createAccount(@NonNull ClientDto clientDto) {
        return AccountDto.builder()
                .balance(0L)
                .client(clientDto)
                .build();
    }

    @Override
    public Optional<AccountDto> getAccount(Long id) {
        log.info("Start found account with id: {}", id);
        return repo.findByAccountId(id)
                .map(accountEntity -> {
                    AccountDto accountDto = mapper.entityToDto(accountEntity);
                    log.info("Object was found successful: {}", accountDto);
                    return Optional.of(accountDto);
                })
                .orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    public Optional<Set<AccountDto>> getAccounts() {
        log.info("Start found all accounts");
        Set<AccountDto> dtoSet = mapper.entitySetToDtoSet(repo.findAll());
        if (dtoSet.isEmpty()) {
            log.error("Objects do not exists!");
            return Optional.empty();
        } else {
            log.info("Was found {} accounts", dtoSet.size());
            return Optional.of(dtoSet);
        }
    }

    @Override
    public Optional<Set<AccountDto>> getAccountsByUserUsername(String username) {
        log.info("Start found accounts of a client with username: {}", username);
        isExist(username);
        Set<AccountDto> dtoSet = mapper.entitySetToDtoSet(repo.findAllByClient_User_Username(username));
        if (dtoSet.size() > 0) {
            return Optional.of(dtoSet);
        } else {
            log.warn("Client with username {} hasn't accounts!", username);
            return Optional.empty();
        }
    }

    private void isExist(@NonNull String username) {
        clientService.getClientByUsername(username);
    }

    @Override
    public Optional<AccountDto> update(Long accountId, Long money, byte operand,
                                       @Nullable String comment) {
        return getAccount(accountId)
                .map(dto -> save(updateBalance(money, dto, operand, comment)))
                .orElseGet(() -> {
                    log.error("An error occurred while trying to update a account with id: {}", accountId);
                    return Optional.empty();
                });
    }

    @Override
    public void transferBetweenAccounts(Long fromId, Long toId, Long money,
                                        @Nullable String comment) {
        update(fromId, money, MINUS, comment);
        update(toId, money, PLUS, comment);
    }

    @Override
    public void transferBetweenAccountAndJkx(Long fromId, Long money, @Nullable String comment) {
        transferBetweenAccounts(fromId, getJkxId(), money, comment);
    }

    @Override
    public void transferBetweenAccountAndTelephone(Long fromId, Long money, @Nullable String comment) {
        transferBetweenAccounts(fromId, getTelephoneId(), money, comment);
    }

    @Override
    public void transferBetweenAccountAndTax(Long fromId, Long money, @Nullable String comment) {
        transferBetweenAccounts(fromId, getTaxId(), money, comment);
    }

    @Override
    public Optional<AccountDto> deleteById(Long id) {
        log.info("Start delete account by id: {}", id);
        return getAccount(id)
                .map(entity -> {
                    repo.delete(mapper.dtoToEntity(entity));
                    return entity;
                });
    }

//        AccountEntity accountEntity = entityManager.find(AccountEntity.class, 534L);
//        entityManager.lock(accountEntity, LockModeType.OPTIMISTIC);

    private Optional<AccountDto> save(AccountDto accountDto) {
        log.info("Start save account");
        return Optional.ofNullable(mapper.entityToDto(
                        repo.save(mapper.dtoToEntity(accountDto))))
                .map(e -> {
                    log.info("Entity was saved successful: {}", e);
                    return Optional.of(e);
                })
                .orElseThrow(() -> new NotSaveException(accountDto, accountDto.getAccountId()));
    }

    @VisibleForTesting
    public AccountDto updateBalance(Long money, AccountDto dto, byte operand,
                                    @Nullable String comment) {
        Long balance = dto.getBalance();
        if (operand == MINUS && balance < money) {
            throw new NoEnoughMoneyException();
        }
        return dto.toBuilder().balance(balance + money * operand)
                .comment(comment)
                .build();
    }

    public Long getJkxId() {
        return getAccountsByUserUsername(jkx)
                .map(e -> e.iterator().next().getAccountId())
                .orElse(null);
    }

    public Long getTelephoneId() {
        return getAccountsByUserUsername(telephone)
                .map(e -> e.iterator().next().getAccountId())
                .orElse(null);
    }

    public Long getTaxId() {
        return getAccountsByUserUsername(tax)
                .map(e -> e.iterator().next().getAccountId())
                .orElse(null);
    }
}