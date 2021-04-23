package com.example.learningapplicationwithshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "question")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Column(name = "question_name", length = 400)
    private String questionName;

    @NotBlank
    @Column(name = "good_answer")
    private String goodAnswer;

    @NotBlank
    @Column(name = "bad_answer1")
    private String badAnswer1;

    @Column(name = "bad_answer2")
    private String badAnswer2;

    @Column(name = "bad_answer3")
    private String badAnswer3;

    @Column(name = "category")
    private String category;

    @Column(name = "picture")
    public String picture;

}
