package com.umc.gusto.domain.review.model.response;

import com.umc.gusto.domain.review.entity.Review;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RandomFeedResponse {
    Long reviewId;
    List<String> images;

    public static RandomFeedResponse of(Review review){
        return RandomFeedResponse.builder()
                .reviewId(review.getReviewId())
                .images(review.getImageList())
                .build();
    }
}
