package com.example.libraryrestapi.service;

import com.example.libraryrestapi.constants.ErrorMessage;
import com.example.libraryrestapi.constants.RentStatus;
import com.example.libraryrestapi.dto.UserRequest;
import com.example.libraryrestapi.dto.RentResponse;
import com.example.libraryrestapi.dto.UserResponse;
import com.example.libraryrestapi.entitiy.Rent;
import com.example.libraryrestapi.entitiy.User;
import com.example.libraryrestapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 주어진 요청으로 회원을 저장하고, 저장된 회원 정보를 포함한 UserResponse 반환하는 메소드
     * @param request 저장할 회원 정보
     * @return 저장된 회원 정보를 포함한 UserResponse
     */
    public UserResponse save(UserRequest request) {
        validateUser(request);
        return new UserResponse(userRepository.save(request.toEntity()));
    }

    /**
     * 주어진 타입에 따라 해당 회원의 대출 내역을 조회하여 반환하는 메소드.
     * @param type 대출 내역 조회 유형 ("ALL" 또는 "RENTED_ONLY")
     * @param userId 조회할 회원의 ID
     * @return 대출 내역을 담은 RentResponse
     * @throws IllegalArgumentException 유효하지 않은 type 일 때 발생하는 예외
     */
    public RentResponse findRentById(String type, Long userId) {
        User user = findUser(userId);
        List<Rent> rents = getRentsByType(type, user);
        return new RentResponse(user, rents);
    }

    /**
     * 등록할 회원의 주민번호와 휴대폰 번호의 중복 여부를 검사하는 메소드.
     * @param request 등록할 회원의 정보
     * @throws IllegalArgumentException 주민번호 또는 휴대폰 번호가 중복될 때 발생하는 예외
     */
    private void validateUser(UserRequest request) {
        if (userRepository.existsByIdNumber(request.getIdNumber()))
            throw new IllegalArgumentException(ErrorMessage.DUPLICATE_ID_NUMBER_ERROR);
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber()))
            throw new IllegalArgumentException(ErrorMessage.DUPLICATE_PHONE_ERROR);
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
     * 해당 회원의 도서 대출 내역을 반환하는 메소드.
     * type 으로 대출 내역 조회 조건을 추가할 수 있다. "ALL"이면 전체 조회, "RENTED_ONLY"이면 대출 중인 것만 조회 가능하다.
     * @param type 대출 내역 조회 타입
     * @param user 조회할 회원
     * @return 조회된 대출 내역 리스트
     * @throws IllegalArgumentException 유효하지 않은 type 일 때 발생하는 예외
     */
    private List<Rent> getRentsByType(String type, User user) {
        return switch (type) {
            case "ALL" -> user.getRents();
            case "RENTED_ONLY" -> user.getRents()
                    .stream().filter(rent -> rent.getReturnStatus() == RentStatus.RENTED).toList();
            default -> throw new IllegalArgumentException(ErrorMessage.INVALID_RENT_HISTORY_TYPE);
        };
    }
}
