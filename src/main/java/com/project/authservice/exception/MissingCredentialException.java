package com.project.authservice.exception;

public class MissingCredentialException extends Exception{
    public MissingCredentialException(String message)
    {
        super(message);
    }
}
