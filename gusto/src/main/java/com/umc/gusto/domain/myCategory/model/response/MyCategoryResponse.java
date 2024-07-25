package com.umc.gusto.domain.myCategory.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Boolean publishCategory;
    Boolean userPublishCategory;
    Integer pinCnt;
}

