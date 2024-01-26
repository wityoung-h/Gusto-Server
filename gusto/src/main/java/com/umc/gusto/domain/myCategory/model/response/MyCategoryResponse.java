package com.umc.gusto.domain.myCategory.model.response;

import com.umc.gusto.global.common.PublishStatus;
import lombok.*;

import java.time.LocalDateTime;

public class MyCategoryResponse {

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyCategoryDTO{
        Long myCategoryId;
        String myCategoryName;
        Integer myCategoryIcon;
        PublishStatus publishCategory;
//        Integer myStoresCnt;
    }
}
