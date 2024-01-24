package com.umc.gusto.domain.myCategory.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MyCategoryResponse {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyCategory{
        Long myCategoryId;
        String myCategoryName;
        Integer myCategoryIcon;
        Integer myStoresCnt;

    }
}
