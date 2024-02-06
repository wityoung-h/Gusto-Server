package com.umc.gusto.domain.review.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.umc.gusto.domain.review.entity.Review;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

public class ReviewResponse {
    @Builder
    @Getter
    public static class ReviewDetailDTO{
        Long storeId;
        String storeName;
        String nickName;
        LocalDate visitedAt;
        //TODO: img고려
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String menuName;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String hashTags;
        Integer taste;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Integer spiciness;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Integer mood;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Integer toilet;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Integer parking;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String comment;
        Integer likeCnt;

        public static ReviewDetailDTO of(Review review, String hashTags){
            return ReviewDetailDTO.builder()
                    .storeId(review.getStore().getStoreId())
                    .storeName(review.getStore().getStoreName())
                    .nickName(review.getUser().getNickname())
                    .visitedAt(review.getVisitedAt())
//                    .img() TODO: img 처리하기
                    .menuName(review.getMenuName())
                    .hashTags(hashTags)
                    .taste(review.getTaste())
                    .spiciness(review.getSpiciness())
                    .mood(review.getMood())
                    .toilet(review.getToilet())
                    .parking(review.getParking())
                    .comment(review.getComment())
                    .likeCnt(review.getLiked())
                    .build();
        }
    }
}
