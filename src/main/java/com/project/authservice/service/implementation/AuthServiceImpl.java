package com.project.authservice.service.implementation;

import com.project.authservice.exception.BadCredentialsException;
import com.project.authservice.exception.UserAlreadyExistsException;
import com.project.authservice.exception.UserNotFoundException;
import com.project.authservice.model.Session;
import com.project.authservice.model.SessionStatus;
import com.project.authservice.model.User;
import com.project.authservice.repository.SessionRepository;
import com.project.authservice.repository.UserRepository;
import com.project.authservice.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public AuthServiceImpl(UserRepository userRepository, SessionRepository sessionRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> signUp(User user) throws UserAlreadyExistsException {
        //1. Check if user exists with same email;
        if(userRepository.findUserByEmail(user.getEmail())!=null)
        {
            throw new UserAlreadyExistsException("Cannot proceed as provided email id: "+user.getEmail()+" is already used");
        }
        user.setCreatedOn(new Date(System.currentTimeMillis()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser=userRepository.save(user);
        savedUser.setPassword(null);
        return Optional.of(savedUser);
    }

    @Override
    public Optional<String> login(String email, String pwd,String ipAddress) throws UserNotFoundException, BadCredentialsException {
        User dbUser=userRepository.findUserByEmail(email);
        if(dbUser==null || dbUser.isDeleted())
        {
            throw new UserNotFoundException("There is no user registered with email id: "+email);
        }
        if(passwordEncoder.matches(pwd, dbUser.getPassword()))
        {

            Session session=new Session();
            session.setUser(dbUser);
            session.setStatus(SessionStatus.ACTIVE);
            session.setIpAddress(ipAddress);
            session.setCreatedOn(new Date(System.currentTimeMillis()));
            session.setExpiringAt(new Date(System.currentTimeMillis()+(60l*24l*60l*60l*1000l)));
            Session savedSession= sessionRepository.save(session);
            HashMap<String,Object> headerMap=new HashMap<>();
            headerMap.put("typ","jwt");
            HashMap<String,Object> claimsMap=new HashMap<>();
            claimsMap.put("userId",dbUser.getId());
            claimsMap.put("email",dbUser.getEmail());
            claimsMap.put("roles",dbUser.getRoles());
            claimsMap.put("session_Id",savedSession.getId());
            claimsMap.put("createdOn",savedSession.getCreatedOn());
            claimsMap.put("expiringAt",savedSession.getExpiringAt());
            String token= Jwts.builder()
                    .header()
                    .add(headerMap)
                    .and()
                    .claims(claimsMap)
                    .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secrete.getBytes()))
                    .compact();
            savedSession.setToken(token);
            sessionRepository.save(session);
            return Optional.of(token);
        }
        else
        {
            throw new BadCredentialsException("Invalid Credentials provided");
        }
    }

    @Override
    public Optional<String> logout(String token, Long userId) throws BadCredentialsException {
        //check if token is valid and active
        Session session=sessionRepository.findByTokenAndUser_IdAndStatus(token,userId,SessionStatus.ACTIVE);
        if(session!=null)
        {
            session.setStatus(SessionStatus.LOGGED_OUT);
            sessionRepository.save(session);
            return Optional.of("Successfully logged out");
        }
        throw new BadCredentialsException("Unable to logout due to bad Credential in token.");
    }

    @Override
    public Optional<SessionStatus> validate(String token, Long userId,String ipAddress) {
        Session session=sessionRepository.findByTokenAndUser_Id(token,userId);
        if(session!=null)
        {
            return Optional.of(session.getStatus());
        }
        return Optional.of(SessionStatus.INVALID);
    }
}
