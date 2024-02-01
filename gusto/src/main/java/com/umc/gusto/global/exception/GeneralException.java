package com.umc.gusto.global.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GeneralException extends RuntimeException{
    private Code code;
}
