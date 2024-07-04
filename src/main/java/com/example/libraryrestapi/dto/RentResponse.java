package com.example.libraryrestapi.dto;

import com.example.libraryrestapi.entitiy.Rent;
import com.example.libraryrestapi.entitiy.User;
import lombok.Getter;

import java.util.List;

@Getter
public class RentResponse {
    private final String name;
    private final String phoneNumber;
    private final List<RentedBook> rentedBooks;

    public RentResponse(User user, List<Rent> rents) {
        this.name = user.getName();
        this.phoneNumber = user.getPhoneNumber();
        rentedBooks = rents.stream().map(RentedBook::new).toList();
    }
}
