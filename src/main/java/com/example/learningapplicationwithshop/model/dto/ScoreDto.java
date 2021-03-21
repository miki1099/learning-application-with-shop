package com.example.learningapplicationwithshop.model.dto;

import com.example.learningapplicationwithshop.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreDto {

    private int id;

    private int score;

    private UserDto userId;

}
