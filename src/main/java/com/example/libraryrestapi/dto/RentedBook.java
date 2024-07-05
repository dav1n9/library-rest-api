package com.example.libraryrestapi.dto;

import com.example.libraryrestapi.constants.RentStatus;
import com.example.libraryrestapi.entitiy.Rent;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RentedBook {
    private final String title;
    private final String author;
    private final RentStatus returnStatus;
    private final LocalDateTime rentDate;

    public RentedBook(Rent rent) {
        this.title = rent.getBook().getTitle();
        this.author = rent.getBook().getAuthor();
        this.returnStatus = rent.getReturnStatus();
        this.rentDate = rent.getRentDate();
    }
}
