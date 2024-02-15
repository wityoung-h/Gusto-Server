package com.umc.gusto.domain.myCategory.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreatePinRequest {
    @NotNull(message = "내 카테고리는 필수 입력 값입니다.")
    Long myCategoryId;
    @NotNull(message = "가게는 필수 입력 값입니다.")
    Long storeId;
}