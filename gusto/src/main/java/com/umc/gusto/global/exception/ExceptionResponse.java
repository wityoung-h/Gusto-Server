package com.umc.gusto.global.exception;

public record ExceptionResponse<T>(
        int errorCode,
        String message
) {
    public static <T> ExceptionResponse<T> from(Code code){
        return new ExceptionResponse<>(code.getCode(), code.getMessage());
    }
}
