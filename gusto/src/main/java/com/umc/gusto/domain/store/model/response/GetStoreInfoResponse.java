package com.umc.gusto.domain.store.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetStoreInfoResponse {
    Long storeId;
    String categoryName;
    String storeName;
    String address;
    String reviewImg;
}