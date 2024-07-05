package com.example.libraryrestapi.entitiy;

import com.example.libraryrestapi.constants.RentStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "id_number", nullable = false, unique = true)
    private String idNumber;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "penalty_end_date")
    private LocalDateTime penaltyEndDate;

    @OneToMany(mappedBy = "user")
    private List<Rent> rents = new ArrayList<>();

    @Builder
    public User(String name, String gender, String idNumber, String phoneNumber, String address) {
        this.name = name;
        this.gender = gender;
        this.idNumber = idNumber;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.penaltyEndDate = null;
    }

    public void setPenalty(LocalDateTime endDate) {
        penaltyEndDate = endDate;
    }

    /**
     * 해당 회원이 대출이 가능한 상태인지 확인하는 메소드.
     * 패널티 기간이 끝났는지 확인하고, 연체 도서가 있는지 확인하여
     * 회원의 대출 가능 여부를 판단한다.
     * @return 회원이 대출 가능하면 true, 불가능하면 false.
     */
    public boolean isRentAllowed() {
        LocalDateTime current = LocalDateTime.now();
        // 패널티 기간이 끝났는지 확인
        boolean isPenaltyPeriodOver = penaltyEndDate == null || current.isAfter(penaltyEndDate);
        // 연체 도서가 있는지 확인
        boolean hasOverdueBooks = rents.stream()
                .anyMatch(rent -> rent.getReturnStatus() == RentStatus.RENTED
                        && rent.getRentDate().plusDays(7).isBefore(current));

        return isPenaltyPeriodOver && !hasOverdueBooks;
    }
}
