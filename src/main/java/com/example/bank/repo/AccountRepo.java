package com.example.bank.repo;

import com.example.bank.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepo extends JpaRepository<AccountEntity, Long> {

    Optional<AccountEntity> findByAccountId(Long id);

    List<AccountEntity> findAllByClient_User_UsernameOrderByAccountId(String username);
}
