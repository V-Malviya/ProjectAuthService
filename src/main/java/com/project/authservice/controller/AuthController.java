package com.project.authservice.controller;

import com.project.authservice.dto.*;
import com.project.authservice.exception.BadCredentialsException;
import com.project.authservice.exception.MissingCredentialException;
import com.project.authservice.exception.UserAlreadyExistsException;
import com.project.authservice.exception.UserNotFoundException;
import com.project.authservice.model.SessionStatus;
import com.project.authservice.model.User;
import com.project.authservice.service.AuthService;
import com.project.authservice.service.RoleService;
import com.project.authservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;
    private RoleService roleService;
    private UserService userService;
    @Autowired
    public AuthController(AuthService authService, RoleService roleService, UserService userService) {
        this.authService = authService;
        this.roleService = roleService;
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto) throws UserAlreadyExistsException, MissingCredentialException {
        if(signUpRequestDto.getEmail()==null || signUpRequestDto.getPassword()==null)
        {
            throw new MissingCredentialException("Please provide valid email id and password");
        }
        User user=authService.signUp(signUpRequestDto.userFromDto()).get();
        return new ResponseEntity<>(UserResponseDto.dtoFromUser(user), HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request) throws MissingCredentialException, UserNotFoundException, BadCredentialsException {
        if(loginRequestDto.getEmail()==null || loginRequestDto.getPassword()==null)
        {
            throw new MissingCredentialException("Please provide email id and password");
        }
        String token=authService.login(loginRequestDto.getEmail(),loginRequestDto.getPassword(),request.getRemoteAddr()).get();
        MultiValueMap<String,String> header=new LinkedMultiValueMap<String,String>();
        header.add("Auth_Token",token);
        return new ResponseEntity<String>("Login successful",header,HttpStatus.OK);
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logOut(@RequestBody LogoutRequestDto logoutRequestDto) throws BadCredentialsException {
        return new ResponseEntity<>(authService.logout(logoutRequestDto.getToken(), logoutRequestDto.getUserId()).get(),HttpStatus.OK);
    }
    @PostMapping("/validate")
    public ResponseEntity<SessionStatus> validate(@RequestBody ValidateRequestDto validateRequestDto, HttpServletRequest request) throws MissingCredentialException {
        if(validateRequestDto.getToken()==null || validateRequestDto.getUserId()==null)
        {
            throw new MissingCredentialException("unable to validate due to insufficient credential provided");
        }
        return new ResponseEntity<>(authService.validate(validateRequestDto.getToken(), validateRequestDto.getUserId(),request.getRemoteAddr()).get(),HttpStatus.OK);
    }
}
