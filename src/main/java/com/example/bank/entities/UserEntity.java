package com.example.bank.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"client", "role"})
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(schema = "bank", name = "usr")
public class UserEntity implements Serializable {
    @Id
    @Column(name = "user_id")
    Long id;

    @NotBlank
    String username;
    @NotBlank
    String password;

    Boolean active;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "client_id")
    @MapsId
    ClientEntity client;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    RoleEntity role;
}
