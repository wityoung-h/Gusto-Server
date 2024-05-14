package com.umc.gusto.domain.review.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.umc.gusto.domain.review.entity.Review;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
public class ReviewDetailResponse {
    Long storeId;
    String storeName;
    LocalDate visitedAt;
    List<String> images;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String menuName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<Long> hashTags;
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

    public static ReviewDetailResponse of(Review review, List<Long> hashTags){
        return ReviewDetailResponse.builder()
                .storeId(review.getStore().getStoreId())
                .storeName(review.getStore().getStoreName())
                .visitedAt(review.getVisitedAt())
                .images(review.getImageList())
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
