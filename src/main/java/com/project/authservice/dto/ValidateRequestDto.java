package com.project.authservice.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ValidateRequestDto {
    private String token;
    private Long userId;
}
