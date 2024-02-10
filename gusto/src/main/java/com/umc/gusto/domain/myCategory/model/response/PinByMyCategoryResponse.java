package com.umc.gusto.domain.myCategory.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PinByMyCategoryResponse{
    Long pinId;
    Long storeId;
    String storeName;
    String address;
    String reviewImg;
    Integer reviewCnt;
}