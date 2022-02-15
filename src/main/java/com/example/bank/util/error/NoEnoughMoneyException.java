package com.example.bank.util.error;

public class NoEnoughMoneyException extends RuntimeException {
    public NoEnoughMoneyException() {
        super("Not enough money on balance");
    }
}
