package com.project.authservice.service;

import com.project.authservice.exception.BadCredentialsException;
import com.project.authservice.exception.UserAlreadyExistsException;
import com.project.authservice.exception.UserNotFoundException;
import com.project.authservice.model.SessionStatus;
import com.project.authservice.model.User;

import java.util.Optional;

public interface AuthService {
    String secrete="IDontKnowAnyRandomSecretekljsefkjsadkfhlakusdlkvujlkjgbkubglkutgbliuvrblkuwanliu" +
            "aynelivulwaekurvniluwellviuaelviugliusdgrbliausgdviuagbsrgkDJgfjhgsdfsjdgvkjsgdugsdjgfskjdgfhsgdjfjhvj " +
            "ksdfblksjbdkjvadkbflkjhlkvjhelrjhekjrhvskjdfkjsvbdkjfhks" +
            "slknfsdfsgdfhfgjfgjhsbrtsrntyn";
    Optional<User> signUp(User user) throws UserAlreadyExistsException;
    Optional<String> login(String email,String pwd,String ipAddress) throws UserNotFoundException, BadCredentialsException;
    Optional<String> logout(String token,Long userId) throws BadCredentialsException;
    Optional<SessionStatus> validate(String token,Long userId,String ipAddress);
}
