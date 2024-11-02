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
public class GetPinStoreInfoResponse {
    Long storeId;
    String storeName;
    String address;
    String category;
    List<String> reviewImg3;
}
