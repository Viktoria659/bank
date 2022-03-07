package com.example.bank.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"accounts", "user"})
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(schema = "bank", name = "client")
public class ClientEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_id_seq")
    @SequenceGenerator(name = "client_id_seq", sequenceName = "bank.client_id_seq", allocationSize = 1)
    @Column(name = "client_id")
    Long id;

    @NotBlank
    String firstname;
    @NotBlank
    String surname;

    String patronymic;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    LocalDateTime birthday;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @JoinColumn(name = "client_id", referencedColumnName = "user_id")
    UserEntity user;

    @OneToMany
    @JoinColumn(name = "account_id", referencedColumnName = "client_id")
    List<AccountEntity> accounts = new ArrayList<>();
}
