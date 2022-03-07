package com.example.bank.util.error;

public class TransferBetweenEqualException extends RuntimeException {
    public TransferBetweenEqualException() {
        super("No transfer between equal object");
    }
}
