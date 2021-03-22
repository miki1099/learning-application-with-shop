package com.example.learningapplicationwithshop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSaveDto {

    private int id;

    private String login;

    private String password;

    private String email;

    private String name;

    private String lastName;

    private AddressDto address;

    private String phone;

}
