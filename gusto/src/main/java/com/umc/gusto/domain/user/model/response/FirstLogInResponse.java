package com.umc.gusto.domain.user.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class FirstLogInResponse {
    private String nickname;
    private String profileImg;
    private String gender;
    private String age;
}
