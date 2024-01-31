package com.umc.gusto.domain.user.model.response;

public record FirstLogInResponse (
    String nickname,
    String profileImg,
    String gender,
    String age
) { }
