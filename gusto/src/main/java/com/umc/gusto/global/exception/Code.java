package com.umc.gusto.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum Code {
    INTERNAL_SEVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "서버 에러 발생, 관리자에게 문의해주세요."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST,41000, "잘못된 요청입니다."),
    NO_PERMISSION(HttpStatus.BAD_REQUEST,41001, "해당 권한이 없습니다."),
    FOR_TEST_ERROR(HttpStatus.BAD_REQUEST,49999, "테스트용 에러"),
    DONT_EXIST_USER(HttpStatus.NOT_FOUND, 40400, "존재하지 않는 유저입니다.")
    ;

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;
}
