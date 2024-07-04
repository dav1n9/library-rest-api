package com.example.libraryrestapi.service;

import com.example.libraryrestapi.constants.RentStatus;
import com.example.libraryrestapi.dto.BookResponse;
import com.example.libraryrestapi.dto.BookRequest;
import com.example.libraryrestapi.entitiy.Book;
import com.example.libraryrestapi.entitiy.Rent;
import com.example.libraryrestapi.entitiy.User;
import com.example.libraryrestapi.repository.BookRepository;
import com.example.libraryrestapi.repository.RentRepository;
import com.example.libraryrestapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final RentRepository rentRepository;
    private final UserRepository userRepository;

    public BookResponse save(BookRequest request) {
        System.out.println(request.getAuthor());
        Book savedBook = bookRepository.save(request.toEntity());
        return new BookResponse(savedBook);
    }

    public List<BookResponse> findAll() {
        return bookRepository.findAll().stream().map(BookResponse::new).toList();
    }

    public BookResponse findById(Long bookId) {
        Book findBook = findBook(bookId);
        return new BookResponse(findBook);
    }

    public Long rentBook(Long bookId, Long userId) {
        User user = findUser(userId);
        if (!user.isRentAllowed()) {
            throw new IllegalArgumentException("The user is not allowed to borrow");
        }
        Book book = findBook(bookId);
        if (!book.getAvailable()) {
            throw new IllegalArgumentException("is already borrowed book");
        }
        Rent save = rentRepository.save(new Rent(user, book));
        return save.getId();
    }

    @Transactional
    public Long returnBook(Long bookId) {
        Rent rent = findRentByBookId(bookId);
        rent.returnBook();

        return rent.getId();
    }

    private Book findBook(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + bookId));
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("not fount user: " + userId));
    }

    private Rent findRentByBookId(Long bookId) {
        return rentRepository.findByBookIdAndReturnStatus(bookId, RentStatus.RENTED)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + bookId));
    }
}
