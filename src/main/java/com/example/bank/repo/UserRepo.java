package com.example.bank.repo;

import com.example.bank.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {

    @Query(value = "select u from UserEntity u " +
            "left join fetch u.client c " +
            "left join fetch c.accounts " +
            "where u.username = :username")
    Optional<UserEntity> findByUsername(@Param("username") String username);
}
