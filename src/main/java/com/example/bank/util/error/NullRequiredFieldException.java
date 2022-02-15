package com.example.bank.util.error;

public class NullRequiredFieldException extends RuntimeException {
    public NullRequiredFieldException(String owner) {
        super(String.format("%s's required field is null", owner));
    }
}
