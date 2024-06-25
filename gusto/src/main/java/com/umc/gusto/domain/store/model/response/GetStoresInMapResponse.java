package com.umc.gusto.domain.store.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetStoresInMapResponse{
    Long storeId;
    Long myCategoryId;
    String storeName;
    Double longitude;
    Double latitude;
}
