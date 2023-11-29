package com.project.authservice.service.implementation;

import com.project.authservice.dto.UserResponseDto;
import com.project.authservice.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserResponseDto getUserDetails() {
        return null;
    }

    @Override
    public void setUserRoles(Long userId, List<Long> roleIds) {

    }
}
