package com.project.authservice.exception;

public class UserAlreadyExistsException extends Exception{
    public UserAlreadyExistsException(String message)
    {
        super(message);
    }
}
