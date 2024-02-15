package com.umc.gusto.domain.user.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ProfileResponse {
    String profileImg;
    String nickname;
    String age;
    String gender;
}
