package com.example.libraryrestapi.entitiy;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "language")
    private String language;

    @Column(name = "publisher")
    private String publisher;

    @CreatedDate
    @Column(name = "registration_date", nullable = false)
    private LocalDateTime registrationDate;

    @Column(name = "available", nullable = false)
    private Boolean available;

    @Builder
    public Book(String title, String author, String language, String publisher, boolean available) {
        this.title = title;
        this.author = author;
        this.language = language;
        this.publisher = publisher;
        this.available = available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
