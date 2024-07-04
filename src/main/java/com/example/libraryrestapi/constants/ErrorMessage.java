package com.example.libraryrestapi.constants;

public class ErrorMessage {
    public static final String NOT_FOUND_USER = "존재하지 않는 회원입니다.";
    public static final String NOT_FOUND_BOOK = "존재하지 않는 도서입니다.";
    public static final String USER_NOT_AVAILABLE_FOR_RENT = "대출이 불가능한 회원입니다.";
    public static final String BOOK_NOT_RENTED = "대출되지 않은 도서입니다.";
    public static final String BOOK_ALREADY_RENTED = "이미 대출된 도서입니다.";
    public static final String USER_HAS_UNRETURNED_BOOKS = "반납하지 않은 도서가 있어 대출이 불가능합니다.";
    public static final String INVALID_RENT_HISTORY_TYPE = "대출 내역을 조회하기에 적합하지 않은 타입입니다.";

}