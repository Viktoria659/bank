package com.example.bank.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder(toBuilder = true)
public class AccountDto {
    Long accountId;
    Long balance;
    String comment;
    LocalDateTime createdDate;
    LocalDateTime modifiedDate;
    ClientDto client;
    Long version;
}
