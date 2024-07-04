package com.example.libraryrestapi.dto;

import com.example.libraryrestapi.entitiy.Book;
import lombok.Getter;

@Getter
public class BookRequest {
    private String title;
    private String author;
    private String language;
    private String publisher;

    public Book toEntity() {
        return Book.builder()
                .title(title)
                .author(author)
                .language(language)
                .publisher(publisher)
                .available(true)
                .build();
    }
}
