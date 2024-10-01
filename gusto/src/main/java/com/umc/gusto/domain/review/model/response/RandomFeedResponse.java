package com.umc.gusto.domain.review.model.response;

import com.umc.gusto.domain.review.entity.Review;
import com.umc.gusto.domain.review.model.FeedVO;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RandomFeedResponse {
    Long reviewId;
    String images;

    public static RandomFeedResponse of(Review review){
        return RandomFeedResponse.builder()
                .reviewId(review.getReviewId())
                .images(review.getImg1())
                .build();
    }

    public static RandomFeedResponse of(FeedVO feedVO){
        return RandomFeedResponse.builder()
                .reviewId(feedVO.getReviewId())
                .images(feedVO.getImage())
                .build();
    }
}
