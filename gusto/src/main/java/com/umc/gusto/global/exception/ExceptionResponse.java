package com.umc.gusto.global.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

public record ExceptionResponse<T>(
        int errorCode,
        String message,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        T result
) {
    public static <T> ExceptionResponse<T> from(Code code, T result){
        return new ExceptionResponse<>(code.getCode(), code.getMessage(), result);
    }
}
