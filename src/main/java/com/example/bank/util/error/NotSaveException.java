package com.example.bank.util.error;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotSaveException extends RuntimeException {
    public NotSaveException(Object o, Long id) {
        super(String.format("Entity with id: %s not save", id));
        log.debug("{}", o);
    }
}
