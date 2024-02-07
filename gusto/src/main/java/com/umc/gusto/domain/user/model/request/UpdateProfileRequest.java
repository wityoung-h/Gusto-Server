package com.umc.gusto.domain.user.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class UpdateProfileRequest {
    String age;
    String gender;
}
