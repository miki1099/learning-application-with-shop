package com.example.learningapplicationwithshop.exceptions;

public class UserDoesNotHaveRequiredField extends RuntimeException{
    public UserDoesNotHaveRequiredField(String message) {
        super(message);
    }
}
