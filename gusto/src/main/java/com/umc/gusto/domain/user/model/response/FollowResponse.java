package com.umc.gusto.domain.user.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class FollowResponse  {
    long followId;
    String nickname;
    String profileImg;
}
