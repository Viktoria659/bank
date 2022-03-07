package com.example.bank.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Value;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Value
@Builder(toBuilder = true)
public class UserDto implements UserDetails, Serializable {
    Long id;
    String username;
    String password;
    Boolean active;
    RoleDto role;
    ClientDto client;

    @Override
    @JsonIgnore
    public List<RoleDto> getAuthorities() {
        return new ArrayList<>(List.of(role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
