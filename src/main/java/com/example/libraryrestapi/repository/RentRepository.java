package com.example.libraryrestapi.repository;

import com.example.libraryrestapi.constants.RentStatus;
import com.example.libraryrestapi.entitiy.Rent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RentRepository extends JpaRepository<Rent, Long> {
    List<Rent> findByUserIdAndReturnStatus(Long userId, RentStatus returnStatus);
    List<Rent> findByUserId(Long userId);
    Optional<Rent> findByBookIdAndReturnStatus(Long bookId, RentStatus returnStatus);
}
