package com.example.learningapplicationwithshop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.format.DateTimeParseException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {SQLIntegrityConstraintViolationException.class})
    protected ResponseEntity<Object> handleDuplicate(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "One of fields already exists!";
        return new ResponseEntity<>(bodyOfResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    protected ResponseEntity<Object> handleUserNotFound(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "User not found with this " + ex.getMessage() + "!";
        return new ResponseEntity<>(bodyOfResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class, BadInputException.class})
    protected ResponseEntity<Object> handleInputNotValid(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "One of fields are not valid!";
        return new ResponseEntity<>(bodyOfResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {QuestionNotFoundException.class})
    protected ResponseEntity<Object> handleQuestionNotFound(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Question not found with this " + ex.getMessage() + "!";
        return new ResponseEntity<>(bodyOfResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();
        return new ResponseEntity<>(bodyOfResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {UserDoesNotHaveRequiredField.class, ProductOutOfStockException.class})
    protected ResponseEntity<Object> userDoesNotHaveRequiredFieldsToPlaceOrder(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = {DateTimeParseException.class})
    protected ResponseEntity<Object> dateTimeParseExceptionHandler(RuntimeException ex) {
        return new ResponseEntity<>("Bad date input format only legal YYYY-MM-DD", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {PasswordDoesNotMatchException.class})
    protected ResponseEntity<Object> passwordDoesNotMatchExceptionHandler(RuntimeException ex) {
        return new ResponseEntity<>("Passwords does not match!", HttpStatus.UNAUTHORIZED);
    }
}
