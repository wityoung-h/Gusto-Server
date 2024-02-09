package com.umc.gusto.domain.store.model.response;

import com.umc.gusto.domain.store.entity.OpeningHours;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class StoreResponse {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getStore{
        Long storeId;
        String storeName;
        String address;
        Map<OpeningHours.BusinessDay, Timing> businessDay;
        String contact;
        List<String> reviewImg3;
        Boolean pin;        // 찜 여부

        // 하루 영업 시간을 나타내는 내부 클래스
        @Getter
        @AllArgsConstructor
        public static class Timing {
            LocalTime openedAt; // 오픈 시간
            LocalTime closedAt; // 마감 시간
        }

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
}
