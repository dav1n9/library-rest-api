package com.example.libraryrestapi.entitiy;

import com.example.libraryrestapi.constants.RentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rent_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "return_status", nullable = false)
    private RentStatus returnStatus;

    @CreatedDate
    @Column(name = "rent_date", nullable = false)
    private LocalDateTime rentDate;

    @LastModifiedDate
    @Column(name = "return_date", nullable = false)
    private LocalDateTime returnDate;

    public Rent(User user, Book book) {
        this.user = user;
        this.book = book;
        this.returnStatus = RentStatus.RENTED;
        this.book.setAvailable(false);  // 대출 불가능 상태
    }

    public void returnBook(boolean isPenalty) {
        if (isPenalty)
            user.setPenalty(LocalDateTime.now().plusDays(14));
        this.returnStatus = RentStatus.RETURNED; // 책 반납
        book.setAvailable(true); // 대출 가능 상태
    }
}
