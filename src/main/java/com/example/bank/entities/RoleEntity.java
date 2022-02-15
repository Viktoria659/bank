package com.example.bank.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(schema = "bank", name = "role")
public class RoleEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_seq")
    @SequenceGenerator(name = "role_id_seq", sequenceName = "role_id_seq", allocationSize = 1)
    @Column(name = "role_id")
    Long roleId;

    @Column(name = "role")
    @NotBlank
    String value;
}
