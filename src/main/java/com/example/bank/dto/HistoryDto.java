package com.example.bank.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder(toBuilder = true)
public class HistoryDto {
    Long accountId;
    Integer rev;
    Short version;
    Long balance;
    LocalDateTime modifiedDate;
    LocalDateTime createdDate;
    String comment;
}
