package com.example.demo.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import static com.example.demo.constants.ErrorMessage.*;

@Data
public class UpdateUserRequest {
    private Long id;

    @NotBlank(message = EMPTY_FIRST_NAME)
    private String firstName;

    @NotBlank(message = EMPTY_LAST_NAME)
    private String lastName;

    private String city;
    private String address;
    private String phoneNumber;
    private String postIndex;
}
