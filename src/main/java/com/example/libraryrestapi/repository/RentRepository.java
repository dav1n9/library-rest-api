package com.example.libraryrestapi.repository;

import com.example.libraryrestapi.constants.RentStatus;
import com.example.libraryrestapi.entitiy.Rent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RentRepository extends JpaRepository<Rent, Long> {
    Optional<Rent> findByBookIdAndReturnStatus(Long bookId, RentStatus returnStatus);
}
