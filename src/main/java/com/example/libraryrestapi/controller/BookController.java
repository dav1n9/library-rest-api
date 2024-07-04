package com.example.libraryrestapi.controller;

import com.example.libraryrestapi.dto.BookResponse;
import com.example.libraryrestapi.dto.BookRequest;
import com.example.libraryrestapi.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    @PostMapping
    public ResponseEntity<BookResponse> saveBook(@RequestBody BookRequest request) {
        return ResponseEntity.ok()
                .body(bookService.save(request));
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookResponse> findBook(@PathVariable Long bookId) {
        return ResponseEntity.ok()
                .body(bookService.findById(bookId));
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> findAllBook() {
        return ResponseEntity.ok()
                .body(bookService.findAll());
    }

    @PostMapping("/{bookId}/rents")
    public ResponseEntity<Long> rentBook(@PathVariable Long bookId, @RequestHeader("User-Id") String userId) {
        return ResponseEntity.ok()
                .body(bookService.rentBook(bookId, Long.parseLong(userId)));
    }

    @PatchMapping("/{bookId}/return")
    public ResponseEntity<Long> returnBook(@PathVariable Long bookId) {
        return ResponseEntity.ok()
                .body(bookService.returnBook(bookId));
    }
}
