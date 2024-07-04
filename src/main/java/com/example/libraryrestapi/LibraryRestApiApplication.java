package com.example.libraryrestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class LibraryRestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryRestApiApplication.class, args);
    }

}
