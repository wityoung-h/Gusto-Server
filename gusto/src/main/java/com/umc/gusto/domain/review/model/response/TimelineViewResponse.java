package com.umc.gusto.domain.review.model.response;

import com.umc.gusto.domain.review.entity.Review;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
public class TimelineViewResponse {
    Long reviewId;
    String storeName;
    LocalDate visitedAt;
    int visitedCount;
    List<String> images;

    public static TimelineViewResponse of(Review review){
        return TimelineViewResponse.builder()
                .reviewId(review.getReviewId())
                .storeName(review.getStore().getStoreName())
                .visitedAt(review.getVisitedAt())
                .images(review.getImageList())
                .build();
    }
}
