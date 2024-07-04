package com.example.libraryrestapi.service;

import com.example.libraryrestapi.constants.ErrorMessage;
import com.example.libraryrestapi.constants.RentHistoryType;
import com.example.libraryrestapi.constants.RentStatus;
import com.example.libraryrestapi.dto.UserRequest;
import com.example.libraryrestapi.dto.RentResponse;
import com.example.libraryrestapi.dto.UserResponse;
import com.example.libraryrestapi.entitiy.Rent;
import com.example.libraryrestapi.entitiy.User;
import com.example.libraryrestapi.repository.RentRepository;
import com.example.libraryrestapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RentRepository rentRepository;

    public UserResponse save(UserRequest request) {
        return new UserResponse(userRepository.save(request.toEntity()));
    }

    public RentResponse findRentById(String type, Long userId) {
        // 사용자 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NullPointerException(ErrorMessage.NOT_FOUND_USER));
        // 타입에 따른 조회
        List<Rent> rents = new ArrayList<>();
        if (type.equals(RentHistoryType.ALL.toString())) {
            rents = rentRepository.findByUserId(userId);
        } else if (type.equals(RentHistoryType.RENTED_ONLY.toString())){
            rents = rentRepository.findByUserIdAndReturnStatus(userId, RentStatus.RENTED);
        }
        return new RentResponse(user, rents);

    }
}
