package com.project.authservice.dto;

import com.project.authservice.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequestDto {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    public User userFromDto()
    {
        User user=new User();
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setEmail(this.email);
        user.setPassword(this.password);
        return user;
    }
}
