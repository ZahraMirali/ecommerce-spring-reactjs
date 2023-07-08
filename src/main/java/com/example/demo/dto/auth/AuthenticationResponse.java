package com.example.demo.dto.auth;

import com.example.demo.dto.user.UserResponse;
import lombok.Data;

@Data
public class AuthenticationResponse {
    private UserResponse user;
    private String token;
}
