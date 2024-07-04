package com.example.libraryrestapi.repository;

import com.example.libraryrestapi.entitiy.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
