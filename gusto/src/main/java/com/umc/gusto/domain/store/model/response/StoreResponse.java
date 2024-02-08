package com.umc.gusto.domain.store.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
        List<String> businessDay;
        LocalTime openedAt;
        LocalTime closedAt;
        String contact;
        List<String> reviewImg3;
        Boolean pin;        // 찜 여부

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getStoreDetail{
        Long storeId;
        String categoryName;
        String storeName;
        String address;
        Boolean pin;        // 찜 여부
        List<String> reviewImg4;
        List<getReviews> reviews;

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getReviews{         // review public 여부 확인
        Long reviewId;                      // 리뷰 id로 페이징 처리
        LocalDate visitedAt;
        String profileImage;
        String nickname;
        Integer liked;
        String comment;
        String img1;
        String img2;
        String img3;
        String img4;
        
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getStoresInMap{
        Long storeId;
        String storeName;
        Float longitude;
        Float latitude;
    }
}
