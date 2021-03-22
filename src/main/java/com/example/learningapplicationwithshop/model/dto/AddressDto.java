package com.example.learningapplicationwithshop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    private int id;

    private String homeNumber;

    private String street;

    private String city;

    private String country;

}
