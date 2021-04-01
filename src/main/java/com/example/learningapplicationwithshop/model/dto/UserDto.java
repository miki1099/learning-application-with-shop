package com.example.learningapplicationwithshop.model.dto;

import com.example.learningapplicationwithshop.model.Question;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private int id;

    private String login;

    private String password;

    private String email;

    private String name;

    private String lastName;

    private AddressDto address;

    private String phone;

    private Set<OrderDto> orders = new HashSet<>();

    private Set<ScoreDto> scores = new HashSet<>();

    Set<RoleDto> roles = new HashSet<>();

    Set<QuestionDto> questionsLearned = new HashSet<>();
}
