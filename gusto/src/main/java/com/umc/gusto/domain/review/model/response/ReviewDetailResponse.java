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
    String comment;
    Integer likeCnt;
    boolean publicCheck;

    public static ReviewDetailResponse of(Review review, List<Long> hashTags){
        return ReviewDetailResponse.builder()
                .storeId(review.getStore().getStoreId())
                .storeName(review.getStore().getStoreName())
                .visitedAt(review.getVisitedAt())
                .images(review.getImageList())
                .menuName(review.getMenuName())
                .hashTags(hashTags)
                .taste(review.getTaste())
                .comment(review.getComment())
                .likeCnt(review.getLiked())
                .publicCheck(review.getPublishReview().isCheck())
                .build();
    }
}
