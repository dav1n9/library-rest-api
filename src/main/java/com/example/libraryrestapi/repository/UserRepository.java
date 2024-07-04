package com.example.libraryrestapi.repository;

import com.example.libraryrestapi.entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
