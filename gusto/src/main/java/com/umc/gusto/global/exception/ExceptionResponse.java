package com.umc.gusto.global.exception;

public record ExceptionResponse<T>(
        boolean success,
        int errorCode,
        String message
) {
    public static <T> ExceptionResponse<T> from(Code code){
        return new ExceptionResponse<>(false,code.getCode(), code.getMessage());
    }
}
