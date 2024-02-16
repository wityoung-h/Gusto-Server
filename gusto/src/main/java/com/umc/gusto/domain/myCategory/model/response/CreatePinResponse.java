package com.umc.gusto.domain.myCategory.model.response;

import com.umc.gusto.global.common.PublishStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePinResponse {
    Long pinId;
}