package com.umc.gusto.domain.myCategory.model.response;

import com.umc.gusto.global.common.BaseEntity;
import com.umc.gusto.global.common.PublishStatus;
import lombok.*;

import java.time.LocalDateTime;

public class MyCategoryResponse {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyCategory{
        Long myCategoryId;
        String myCategoryName;
        Integer myCategoryIcon;
        PublishStatus publishCategory;
        Integer pinCnt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PinByMyCategory{
        Long pinId;
        Long storeId;
        String storeName;
        String address;
        String reviewImg;
        Integer reviewCnt;
    }

}
