package com.umc.gusto.domain.user.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequest {
    String provider;
    String providerId;
    String accessToken;
}
