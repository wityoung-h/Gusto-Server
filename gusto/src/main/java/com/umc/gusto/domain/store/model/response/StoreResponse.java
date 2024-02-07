package com.umc.gusto.domain.store.model.response;

import com.umc.gusto.domain.store.entity.OpeningHours;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalTime;

public class StoreResponse {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getStore{
        Long storeId;
        String storeName;
        String address;
        OpeningHours.BusinessDay businessDay;
        LocalTime openedAt;
        LocalTime closedAt;
        String contact;
//        List<> reviewImg;
        Boolean pin;        // 찜 여부

    }
}
