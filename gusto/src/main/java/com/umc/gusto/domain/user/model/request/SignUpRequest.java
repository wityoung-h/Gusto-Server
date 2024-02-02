package com.umc.gusto.domain.user.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpRequest {
    String nickname;
    String age;
    String gender;
    String profileImg;
}
