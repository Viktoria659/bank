package com.example.bank.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "client")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Audited
@EntityListeners(AuditingEntityListener.class)
@Table(schema = "bank", name = "account")
public class AccountEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_id_seq")
    @SequenceGenerator(name = "account_id_seq", sequenceName = "account_id_seq", allocationSize = 1)
    @Column(name = "account_id")
    Long accountId;

    @NotNull
    Long balance;

    String comment;

    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    LocalDateTime createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    LocalDateTime modifiedDate;

    @NotAudited
    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    ClientEntity client;

    @Version
    Long version;
}
