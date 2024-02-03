package com.umc.gusto.domain.user.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class FirstLogInResponse {
    @JsonProperty
    private String nickname;
    @JsonProperty
    private String profileImg;
    @JsonProperty
    private String gender;
    @JsonProperty
    private String age;
}
