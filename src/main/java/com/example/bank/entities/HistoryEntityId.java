package com.example.bank.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HistoryEntityId implements Serializable {
    @Column(name = "account_id")
    Long accountId;
    Integer rev;
}
