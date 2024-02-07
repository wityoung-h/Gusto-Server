package com.umc.gusto.domain.store.model.response;

import com.umc.gusto.domain.store.entity.OpeningHours;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

public class StoreResponse {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getStore{
        Long storeId;
        String storeName;
        String address;
        List<OpeningHours.BusinessDay> businessDay;
        Time openedAt;
        Time closedAt;
        String contact;
//        List<> reviewImg;
        Boolean pin;        // 찜 여부

    }
}
