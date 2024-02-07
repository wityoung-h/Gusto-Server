package com.umc.gusto.domain.user.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class ProfileResponse {
    private String nickname;
    private int review;
    private int pin;
    private long follower;
    private boolean followed;
}
