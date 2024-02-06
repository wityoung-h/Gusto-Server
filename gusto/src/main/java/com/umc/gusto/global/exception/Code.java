package com.umc.gusto.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum Code {
    INTERNAL_SEVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "서버 에러 발생, 관리자에게 문의해주세요."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST,400, "잘못된 요청입니다."),
    NO_PERMISSION(HttpStatus.FORBIDDEN,403, "해당 권한이 없습니다."),

    //User 관련 에러 +0
    USER_DUPLICATE_NICKNAME(HttpStatus.CONFLICT, 409101, "이미 사용중인 닉네임입니다."),
    DONT_EXIST_USER(HttpStatus.NOT_FOUND, 404101, "존재하지 않는 유저입니다."),

    //Store 관련 에러 +1
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, 404101, "존재하지 않는 가게입니다."),

    //Review 관련 에러 +2
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, 404201, "존재하지 않는 리뷰입니다."),
    HASHTAG_NOT_FOUND(HttpStatus.NOT_FOUND, 404202, "존재하지 않는 해시태그입니다."),
    USER_NO_PERMISSION_FOR_REVIEW(HttpStatus.FORBIDDEN, 403203, "해당 유저는 해당 리뷰에 대한 권한이 없습니다."),

    //Route 관련 에러 +3

    //Group 관련 에러 +4

    //myCategory 관련 에러 +5
    MYCATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, 404501, "존재하지 않는 카테고리입니다."),
    MYCATEGORY_DUPLICATE_NAME(HttpStatus.CONFLICT, 404502, "이미 존재하는 카테고리입니다."),
    USER_NO_PERMISSION_FOR_MYCATEGORY(HttpStatus.FORBIDDEN, 404503, "해당 유저는 해당 카테고리에 대한 권한이 없습니다."),
    PIN_NOT_FOUND(HttpStatus.NOT_FOUND, 404504,"존재하지 않는 찜(Pin) 입니다"),

    // token 관련 에러 +6
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, 401601, "유효하지 않은 토큰입니다."),


    FOR_TEST_ERROR(HttpStatus.BAD_REQUEST,49999, "테스트용 에러")


    ;

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;
}
