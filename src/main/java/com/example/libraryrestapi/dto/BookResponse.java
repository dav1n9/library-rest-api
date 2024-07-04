package com.example.libraryrestapi.dto;

import com.example.libraryrestapi.entitiy.Book;
import lombok.Getter;

@Getter
public class BookResponse {
    private final Long id;
    private final String title;
    private final String author;
    private final String language;
    private final String publisher;
    private final boolean available;

    public BookResponse(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.language = book.getLanguage();
        this.publisher = book.getPublisher();
        this.available = book.getAvailable();
    }
}
