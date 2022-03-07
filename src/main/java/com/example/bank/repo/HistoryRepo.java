package com.example.bank.repo;

import com.example.bank.entities.HistoryEntity;
import com.example.bank.entities.HistoryEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepo extends JpaRepository<HistoryEntity,HistoryEntityId> {

        List<HistoryEntity> findAllByHistoryId_AccountIdOrderByHistoryId_Rev(Long id);
}
