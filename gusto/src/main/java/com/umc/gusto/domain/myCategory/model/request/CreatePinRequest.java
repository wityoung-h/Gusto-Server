package com.umc.gusto.domain.myCategory.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreatePinRequest {
    @NotNull
    Long myCategoryId;
    @NotNull
    Long storeId;
}