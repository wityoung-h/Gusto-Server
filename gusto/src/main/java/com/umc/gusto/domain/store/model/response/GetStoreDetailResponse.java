package com.umc.gusto.domain.store.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetStoreDetailResponse{
    Long storeId;
    String categoryName;
    String storeName;
    String address;
    Boolean pin;        // 찜 여부
    List<String> reviewImg4;
    List<GetReviewsResponse> reviews;

}
