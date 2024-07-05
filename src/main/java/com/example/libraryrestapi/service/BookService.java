package com.example.libraryrestapi.service;

import com.example.libraryrestapi.constants.ErrorMessage;
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

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final RentRepository rentRepository;
    private final UserRepository userRepository;

    public BookResponse save(BookRequest request) {
        return new BookResponse(bookRepository.save(request.toEntity()));
    }

    public List<BookResponse> findAll() {
        return bookRepository.findAll().stream().map(BookResponse::new).toList();
    }

    public BookResponse findById(Long bookId) {
        return new BookResponse(findBook(bookId));
    }

    /**
     * 책을 대출하는 메소드.
     * 주어진 도서 id와 회원 id를 사용하여 회원과 도서를 찾고,
     * 대출 가능 여부를 검증한 후 대출 기록을 생성하여 저장한다.
     * @param bookId 대출할 도서의 id
     * @param userId 대출할 회원의 id
     * @return 생성된 대출 기록의 id
     * @throws IllegalArgumentException 회원이 대출이 불가능하거나 도서가 대출 중인 경우 발생
     */
    public Long rentBook(Long bookId, Long userId) {
        User user = findUser(userId);
        validateUserForRent(user);

        Book book = findBook(bookId);
        validateBookForRent(book);

        Rent save = rentRepository.save(new Rent(user, book));
        return save.getId();
    }

    /**
     * 해당 도서 id로 대출된 도서를 반납 처리하는 메소드.
     * 대출된 도서를 찾고 해당 도서를 반납처리한다.
     * 만약 사용자가 7일 이내에 반납하지 않은 경우 패널티를 부여한다.
     * @param bookId 반납할 도서 id
     * @return 반납된 도서 id
     */
    @Transactional
    public Long returnBook(Long bookId) {
        Rent rent = findRentByBookId(bookId);

        long daysBetween = ChronoUnit.DAYS.between(rent.getRentDate(), LocalDateTime.now());
        if (daysBetween >= 7) rent.returnBook(true);
        else rent.returnBook(false);

        return rent.getId();
    }

    /**
     * 등록된 도서인지 확인하는 메소드.
     * @param bookId 확인할 도서 id
     * @return 등록된 도서
     * @throws NullPointerException 등록된 도서가 없을 경우 발생하는 예외
     */
    private Book findBook(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new NullPointerException(ErrorMessage.NOT_FOUND_BOOK));
    }

    /**
     * 존재하는 회원인지 확인하는 메소드.
     * @param userId 확인할 회원 id
     * @return 회원
     * @throws NullPointerException 해당하는 회원이 없을 경우 발생하는 예외
     */
    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NullPointerException(ErrorMessage.NOT_FOUND_USER));
    }

    /**
     * 대출 중인 도서 중에서 도서 id가 bookId 인 대출 정보를 반환하는 메소드.
     * @param bookId 검색할 도서 id
     * @return 대출 중인 bookId의 대출 정보
     * @throws IllegalArgumentException 대출 중이 아닌 도서 ID를 입력한 경우 발생하는 예외
     */
    private Rent findRentByBookId(Long bookId) {
        return rentRepository.findByBookIdAndReturnStatus(bookId, RentStatus.RENTED)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.BOOK_NOT_RENTED));
    }

    /**
     * 대출을 할 수 있는 회원인지 확인하는 메소드.
     * @param user 확인할 회원
     * @throws IllegalArgumentException 대출이 불가능한 회원인 경우 발생하는 예외
     */
    private void validateUserForRent(User user) {
        if (!isRentAllowed(user)) {
            throw new IllegalArgumentException(ErrorMessage.USER_NOT_AVAILABLE_FOR_RENT);
        }
    }

    /**
     * 해당 회원이 대출이 가능한 상태인지 확인하는 메소드.
     * 패널티 기간이 끝났는지 확인하고, 연체 도서가 있는지 확인하여
     * 회원의 대출 가능 여부를 판단한다.
     * @return 회원이 대출 가능하면 true, 불가능하면 false.
     */
    private boolean isRentAllowed(User user) {
        LocalDateTime current = LocalDateTime.now();
        // 패널티 기간이 끝났는지 확인
        boolean isPenaltyPeriodOver = user.getPenaltyEndDate() == null || current.isAfter(user.getPenaltyEndDate());
        // 연체 도서가 있는지 확인
        boolean hasOverdueBooks = user.getRents().stream()
                .anyMatch(rent -> rent.getReturnStatus() == RentStatus.RENTED
                        && rent.getRentDate().plusDays(7).isBefore(current));

        return isPenaltyPeriodOver && !hasOverdueBooks;
    }

    /**
     * 대출이 가능한 도서인지 확인하는 메소드.
     * @param book 확인할 도서
     * @throws IllegalArgumentException 대출할 수 없는 도서인 경우 발생하는 예외
     */
    private void validateBookForRent(Book book) {
        if (!book.getAvailable()) {
            throw new IllegalArgumentException(ErrorMessage.BOOK_ALREADY_RENTED);
        }
    }
}
