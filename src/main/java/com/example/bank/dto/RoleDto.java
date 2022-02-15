package com.example.bank.dto;

import lombok.Builder;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;

@Value
@Builder(toBuilder = true)
public class RoleDto implements GrantedAuthority {
    Long roleId;
    String value;

    @Override
    public String getAuthority() {
        return value;
    }
}
