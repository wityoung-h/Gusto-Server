package com.umc.gusto.domain.review.model.response;

import com.umc.gusto.domain.review.entity.Review;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class InstaViewResponse {
    Long reviewId;
    List<String> images;

    public static InstaViewResponse of(Review review){
        return InstaViewResponse.builder()
                .reviewId(review.getReviewId())
                .images(review.getImageList())
                .build();
    }
}
