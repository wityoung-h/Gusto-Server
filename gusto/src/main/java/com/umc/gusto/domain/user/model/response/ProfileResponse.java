package com.umc.gusto.domain.user.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ProfileResponse {
    private String nickname;
    private String profileImg;
    private String age;
    private String gender;
}
