package com.example.demo.dto.review;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import static com.example.demo.constants.ErrorMessage.FILL_IN_THE_INPUT_FIELD;

@Data
public class ReviewRequest {

    private Long perfumeId;

    @NotBlank(message = FILL_IN_THE_INPUT_FIELD)
    private String author;

    @NotBlank(message = FILL_IN_THE_INPUT_FIELD)
    private String message;

    @NotNull(message = "Choose perfume rating")
    private Integer rating;
}
