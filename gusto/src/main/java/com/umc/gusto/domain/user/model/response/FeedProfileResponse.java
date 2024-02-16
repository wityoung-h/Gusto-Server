package com.umc.gusto.domain.user.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class FeedProfileResponse {
    private String nickname;
    private String profileImg;
    private int review;
    private long following;
    private long follower;
    private boolean followed;
}
