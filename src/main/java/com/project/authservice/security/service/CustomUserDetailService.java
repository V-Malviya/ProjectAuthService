package com.project.authservice.security.service;

import com.project.authservice.model.User;
import com.project.authservice.repository.UserRepository;
import com.project.authservice.security.model.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private UserRepository userRepository;
    @Autowired
    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findUserByEmail(username);
        if(user==null)
        {
            throw new UsernameNotFoundException(username+" is not associated with any user");
        }
        CustomUserDetails customUserDetails=new CustomUserDetails(user);
        return customUserDetails;
    }
}
