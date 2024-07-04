package com.example.libraryrestapi.dto;

import com.example.libraryrestapi.entitiy.User;
import lombok.Getter;

@Getter
public class UserResponse {
    private final Long id;
    private final String name;
    private final String gender;
    private final String phoneNumber;
    private final String address;

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.gender = user.getGender();
        this.phoneNumber = user.getPhoneNumber();
        this.address = user.getAddress();
    }
}
