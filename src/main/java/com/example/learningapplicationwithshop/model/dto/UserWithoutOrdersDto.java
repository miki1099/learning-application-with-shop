package com.example.learningapplicationwithshop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserWithoutOrdersDto {

    private int id;

    private String login;

    private String password;

    private String email;

    private String name;

    private String lastName;

    private AddressDto address;

    private String phone;

}
