package com.project.authservice.dto;

import com.project.authservice.model.Role;
import com.project.authservice.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter@Setter
public class UserResponseDto {
    private String email;
    private Set<Role> roles=new HashSet<>();
    public static UserResponseDto dtoFromUser(User user)
    {
        UserResponseDto responseDto=new UserResponseDto();
        responseDto.setEmail(user.getEmail());
        responseDto.setRoles(user.getRoles());
        return responseDto;
    }
}
