package com.example.libraryrestapi.dto;

import com.example.libraryrestapi.entitiy.User;
import lombok.Getter;

@Getter
public class UserRequest {

    private String name;
    private String gender;
    private String idNumber;
    private String phoneNumber;
    private String address;

    public User toEntity() {
        return User.builder()
                .name(name)
                .gender(gender)
                .idNumber(idNumber)
                .phoneNumber(phoneNumber)
                .address(address)
                .build();
    }
}
