package com.project.authservice.service;

import com.project.authservice.dto.UserResponseDto;

import java.util.List;

public interface UserService {
    UserResponseDto getUserDetails();
    void setUserRoles(Long userId, List<Long> roleIds);
}
