package com.example.demo.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

import static com.example.demo.constants.ErrorMessage.*;

@Data
public class PasswordResetRequest {

    private String email;

    @Size(min = 6, max = 16, message = PASSWORD_CHARACTER_LENGTH)
    private String password;

    @Size(min = 6, max = 16, message = PASSWORD2_CHARACTER_LENGTH)
    private String password2;
}
