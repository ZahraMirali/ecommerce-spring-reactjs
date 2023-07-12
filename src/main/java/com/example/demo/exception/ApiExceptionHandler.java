package com.example.demo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApiRequestException.class)
    public ResponseEntity<String> handleApiRequestException(ApiRequestException exception) {
        return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }

    @ExceptionHandler(PasswordConfirmationException.class)
    public ResponseEntity<PasswordConfirmationException> handlePasswordConfirmationException(PasswordConfirmationException exception) {
        return ResponseEntity.badRequest().body(new PasswordConfirmationException(exception.getPassword2Error()));
    }

    @ExceptionHandler(PasswordException.class)
    public ResponseEntity<PasswordException> handlePasswordException(PasswordException exception) {
        return ResponseEntity.badRequest().body(new PasswordException(exception.getPasswordError()));
    }

    @ExceptionHandler(EmailException.class)
    public ResponseEntity<EmailException> handleEmailError(EmailException exception) {
        return ResponseEntity.badRequest().body(new EmailException(exception.getEmailError()));
    }

    @ExceptionHandler(CaptchaException.class)
    public ResponseEntity<CaptchaException> handleCaptchaException(CaptchaException exception) {
        return ResponseEntity.badRequest().body(new CaptchaException(exception.getCaptchaError()));
    }

    @ExceptionHandler(InputFieldException.class)
    public ResponseEntity<Map<String, String>> handleInputFieldException(InputFieldException exception) {
        InputFieldException inputFieldException = new InputFieldException(exception.getBindingResult());
        return ResponseEntity.badRequest().body(inputFieldException.getErrorsMap());
    }
}

// By using @ControllerAdvice, you can define global exception handling strategies, such as handling specific exception types, applying common error response formats, or performing additional actions for specific exceptions. This helps in centralizing exception handling and improving the overall maintainability of your application.
// When the framework detects an InputFieldException being thrown within the controller method, it searches for an appropriate exception handler method annotated with @ExceptionHandler and matching the exception type. Once the matching exception handler method is found, it is automatically invoked, allowing you to handle the exception and provide a customized response to the client.

// ResponseEntity.badRequest(): This method is a convenient shortcut for creating a ResponseEntity instance with the status code 400 Bad Request (ResponseEntity.status(HttpStatus.BAD_REQUEST)). It can be used when you want to indicate that the client's request is invalid or malformed.