package com.project.authservice.exception;

public class BadCredentialsException extends Exception{
    public BadCredentialsException(String message)
    {
        super(message);
    }
}
