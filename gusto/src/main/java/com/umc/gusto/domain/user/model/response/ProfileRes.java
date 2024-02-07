package com.umc.gusto.domain.user.model.response;

public record ProfileRes(
        String nickname,
        int review,
        int pin,
        long follower,
        boolean follwed
) {
}
