package com.umc.gusto.domain.myCategory.model.response;

import com.umc.gusto.global.common.PublishStatus;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyCategoryResponse{
    Long myCategoryId;
    String myCategoryName;
    String myCategoryScript;
    Integer myCategoryIcon;
    PublishStatus userPublishCategory;
    PublishStatus publishCategory;
    Integer pinCnt;
}

