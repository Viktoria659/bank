package com.example.bank.dto;

import lombok.Builder;
import lombok.Value;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Value
@Builder(toBuilder = true)
public class ClientDto {
    Long id;
    String firstname;
    String surname;
    String patronymic;
    Date birthday;
    UserDto user;
    List<AccountDto> accounts = new ArrayList<>();

    @Override
    public String toString() {
        return "ClientDto(id=" + this.getId() + ", firstname=" + this.getFirstname() + ", surname=" +
                this.getSurname() + ", patronymic=" + this.getPatronymic() + ", birthday=" + this.getBirthday() +
                ", user=" + this.getUser() + ", accountsSize=" + this.getAccounts().size() + ")";
    }
}
