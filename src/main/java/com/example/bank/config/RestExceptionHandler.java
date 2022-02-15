package com.example.bank.config;

import com.example.bank.util.error.NoEnoughMoneyException;
import com.example.bank.util.error.NotFoundException;
import com.example.bank.util.error.NotSaveException;
import com.example.bank.util.error.NullRequiredFieldException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFoundEx(RuntimeException ex, WebRequest request) {
        return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({NoEnoughMoneyException.class, NotSaveException.class, NullRequiredFieldException.class})
    protected ResponseEntity<Object> handleEntityBadRequestEx(RuntimeException ex, WebRequest request) {
        return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
    }
}
