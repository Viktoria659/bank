package com.example.bank.repo;

import com.example.bank.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepo extends JpaRepository<ClientEntity, Long> {

    Optional<ClientEntity> findByUser_Username(String username);
}
