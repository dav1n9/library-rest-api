package com.example.libraryrestapi.entitiy;

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
}
