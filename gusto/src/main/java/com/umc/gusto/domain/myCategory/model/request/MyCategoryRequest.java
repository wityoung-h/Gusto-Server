package com.umc.gusto.domain.myCategory.model.request;

import com.umc.gusto.global.common.PublishStatus;
import lombok.Getter;

public class MyCategoryRequest {

    @Getter
    public static class createMyCategoryDTO {
        String myCategoryName;
        String myCategoryScript;
        Integer myCategoryIcon;
        PublishStatus publishCategory;

    }
}
