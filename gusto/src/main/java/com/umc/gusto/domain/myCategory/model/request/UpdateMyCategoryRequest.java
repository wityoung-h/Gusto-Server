package com.umc.gusto.domain.myCategory.model.request;

import com.umc.gusto.global.common.PublishStatus;
import lombok.Getter;

@Getter
public class UpdateMyCategoryRequest {
    String myCategoryName;
    String myCategoryScript;
    Integer myCategoryIcon;
    PublishStatus publishCategory = PublishStatus.PUBLIC;

}