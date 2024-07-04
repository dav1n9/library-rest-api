package com.example.libraryrestapi.controller;

import com.example.libraryrestapi.dto.UserRequest;
import com.example.libraryrestapi.dto.RentResponse;
import com.example.libraryrestapi.dto.UserResponse;
import com.example.libraryrestapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @PostMapping
    public ResponseEntity<UserResponse> saveUser(@RequestBody UserRequest request) {
        return ResponseEntity.ok()
                .body(userService.save(request));
    }

    @GetMapping("/rents")
    public ResponseEntity<RentResponse> findRent(@RequestParam String type, @RequestHeader("User-Id") String userId) {
        return ResponseEntity.ok()
                .body(userService.findRentById(type, Long.parseLong(userId)));
    }
}
