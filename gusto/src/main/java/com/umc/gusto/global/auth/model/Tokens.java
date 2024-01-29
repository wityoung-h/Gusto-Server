package com.umc.gusto.global.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Tokens {
    private String accessToken;
    private String refreshToken;
}
