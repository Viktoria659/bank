package com.example.bank.repo;

import com.example.bank.entities.HistoryEntity;
import com.example.bank.entities.HistoryEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface HistoryRepo extends JpaRepository<HistoryEntity,HistoryEntityId> {

    Set<HistoryEntity> findAllByHistoryId_AccountId(Long id);
}
