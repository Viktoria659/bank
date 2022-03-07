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

import java.util.List;
import java.util.Optional;

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
    public Optional<List<ClientDto>> getClients() {
        log.info("Start found all clients");
        List<ClientDto> dtoList = mapper.entityToDto(repo.findAll());
        if (dtoList.isEmpty()) {
            log.error("Objects do not exists!");
            return Optional.empty();
        } else {
            setAccounts(dtoList);
            log.info("Was found {} clients", dtoList.size());
            return Optional.of(dtoList);
        }
    }

    private void setAccounts(List<ClientDto> clientDtoList) {
        setAccountHelper(accountService.getAccounts(), clientDtoList);
    }

    private void setAccount(ClientDto clientDto) {
        String username = clientDto.getUser().getUsername();
        setAccountHelper(accountService.getAccountsByUserUsername(username), List.of(clientDto));
    }

    private void setAccountHelper(Optional<List<AccountDto>> accountDtoList, List<ClientDto> clientDtoList) {
        log.info("Start add accounts to clients");
        accountDtoList
                .ifPresent(accountSet -> accountSet.forEach(account -> {
                            Long clientId = account.getClient().getId();
                            clientDtoList.stream()
                                    .filter(clientDto -> clientDto.getId().equals(clientId))
                                    .forEach(clientDto -> clientDto.getAccounts().add(account));
                        })
                );
    }
}
