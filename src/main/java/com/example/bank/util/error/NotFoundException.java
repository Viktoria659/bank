package com.example.bank.util.error;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Long id) {
        super(String.format("Object with id: %s does not exist", id));
    }

    public NotFoundException(String username) {
        super(String.format("Object with username: %s does not exist", username));
    }
}
