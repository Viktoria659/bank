package com.example.bank.repo;

import com.example.bank.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface AccountRepo extends JpaRepository<AccountEntity, Long> {

    Optional<AccountEntity> findByAccountId(Long id);

    Set<AccountEntity> findAllByClient_User_Username(String username);
}
