package com.umc.gusto.domain.review.model.response;

import com.umc.gusto.domain.review.entity.Review;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class BasicViewResponse {
    Long reviewId;
    List<String> images;

    public static BasicViewResponse of(Review review){
        return BasicViewResponse.builder()
                .reviewId(review.getReviewId())
                .images(review.getImageList())
                .build();
    }
}
