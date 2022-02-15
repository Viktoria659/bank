package com.example.bank.service;

import com.example.bank.dto.AccountDto;
import com.example.bank.dto.ClientDto;
import com.example.bank.mapper.ClientMapper;
import com.example.bank.repo.ClientRepo;
import com.example.bank.util.error.NotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

/**
 * @author Filippova_Viktoria
 */

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClientServiceImpl implements ClientService {

    ClientMapper mapper = Mappers.getMapper(ClientMapper.class);
    ClientRepo repo;
    AccountService accountService;

    @Override
    public Optional<ClientDto> getClient(Long id) {
        log.info("Start found client with id: {}", id);
        return repo.findById(id)
                .map(clientEntity -> {
                    ClientDto clientDto = mapper.entityToDto(clientEntity);
                    log.info("Object was found successful: {}", clientDto);
                    setAccount(clientDto);
                    return Optional.of(clientDto);
                })
                .orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    public Optional<ClientDto> getClientByUsername(String username) {
        log.info("Start found clientId by username: {}", username);
        return repo.findByUser_Username(username)
                .map(clientEntity -> {
                            ClientDto clientDto = mapper.entityToDto(clientEntity);
                            log.info("Object was found successful: {}", clientDto);
                            return Optional.of(clientDto);
                        })
                .orElseThrow(() -> new NotFoundException(username));
    }

    @Override
    public Optional<Set<ClientDto>> getClients() {
        log.info("Start found all clients");
        Set<ClientDto> dtoSet = mapper.entitySetToDtoSet(repo.findAll());
        if (dtoSet.isEmpty()) {
            log.error("Objects do not exists!");
            return Optional.empty();
        } else {
            setAccounts(dtoSet);
            log.info("Was found {} clients", dtoSet.size());
            return Optional.of(dtoSet);
        }
    }

    private void setAccounts(Set<ClientDto> clientDtoSet) {
        setAccountHelper(accountService.getAccounts(), clientDtoSet);
    }

    private void setAccount(ClientDto clientDto) {
        String username = clientDto.getUser().getUsername();
        setAccountHelper(accountService.getAccountsByUserUsername(username), Set.of(clientDto));
    }

    private void setAccountHelper(Optional<Set<AccountDto>> accountDtoSet, Set<ClientDto> clientDtoSet) {
        log.info("Start add accounts to clients");
        accountDtoSet
                .ifPresent(accountSet -> accountSet.forEach(account -> {
                            Long clientId = account.getClient().getId();
                            clientDtoSet.stream()
                                    .filter(clientDto -> clientDto.getId().equals(clientId))
                                    .forEach(clientDto -> clientDto.getAccounts().add(account));
                        })
                );
    }
}
