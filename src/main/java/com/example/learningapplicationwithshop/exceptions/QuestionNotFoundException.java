package com.example.learningapplicationwithshop.exceptions;

public class QuestionNotFoundException extends RuntimeException{

    public QuestionNotFoundException(String message) {
        super(message);
    }
}
