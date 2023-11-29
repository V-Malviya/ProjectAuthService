package com.project.authservice.controller;

import com.project.authservice.exception.BadCredentialsException;
import com.project.authservice.exception.MissingCredentialException;
import com.project.authservice.exception.UserAlreadyExistsException;
import com.project.authservice.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(UserAlreadyExistsException.class)
    ResponseEntity<String> userAlreadyExistException(Exception e)
    {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
    }
    @ExceptionHandler(MissingCredentialException.class)
    ResponseEntity<String> missingCredentialException(Exception e)
    {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
    }
    @ExceptionHandler(UserNotFoundException.class)
    ResponseEntity<String> userNotFoundException(Exception e)
    {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
    }
    @ExceptionHandler(BadCredentialsException.class)
    ResponseEntity<String> badCredentialException(Exception e)
    {
        return new ResponseEntity<>(e.getMessage(),HttpStatus.OK);
    }
}
