package com.example.bank.entities;


import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(schema = "bank", name = "account_aud")
public class HistoryEntity {
    @EmbeddedId
    HistoryEntityId historyId;

    @Column(name = "revtype")
    Short version;

    @NotNull
    Long balance;

    @Column(name = "modified_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    LocalDateTime modifiedDate;

    @Column(name = "created_date", nullable = false, updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    LocalDateTime createdDate;

    String comment;
}
