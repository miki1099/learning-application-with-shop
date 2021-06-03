package com.example.learningapplicationwithshop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreDto {

    private int id;

    private int score;

    private LocalDate scoreDate;

    private String category;

    private Double testTime;
}
