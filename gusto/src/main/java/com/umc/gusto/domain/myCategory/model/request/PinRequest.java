package com.umc.gusto.domain.myCategory.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class PinRequest {

    @Getter
    public static class createPin {
        Long myCategoryId;
        Long storeId;
    }
}
