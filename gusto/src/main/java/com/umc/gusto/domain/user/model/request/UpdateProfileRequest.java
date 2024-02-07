package com.umc.gusto.domain.user.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class UpdateProfileRequest {
    String age;
    String gender;
}
