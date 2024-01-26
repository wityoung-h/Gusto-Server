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
    public static class MyCategoryDTO{
        Long myCategoryId;
        String myCategoryName;
        Integer myCategoryIcon;
        PublishStatus publishCategory;
        Integer myStoresCnt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyStoreByMyCategoryDTO{
        Long storeId;
        String storeName;
        String address;
        String reviewImg;
        Integer reviewCnt;
    }

}
