package com.example.learningapplicationwithshop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {

    private int id;

    private String questionName;

    private String goodAnswer;

    private String badAnswer1;

    private String badAnswer2;

    private String badAnswer3;

    public Byte[] picture;

}
