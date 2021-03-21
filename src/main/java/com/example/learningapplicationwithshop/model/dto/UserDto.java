package com.example.learningapplicationwithshop.model.dto;

import com.example.learningapplicationwithshop.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private int id;

    private String login;

    private String password;

    private String email;

    private String name;

    private String lastName;

    private Address address;

    private String phone;

    private Set<OrderDto> orders = new HashSet<>();

    private Set<ScoreDto> scores = new HashSet<>();

    Set<RoleDto> roles = new HashSet<>();

}
