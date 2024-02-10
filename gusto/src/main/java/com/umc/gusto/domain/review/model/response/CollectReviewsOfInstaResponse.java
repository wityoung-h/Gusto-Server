package com.umc.gusto.domain.review.model.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CollectReviewsOfInstaResponse {
    List<BasicViewResponse> reviews;
    boolean hasNext;

    public static CollectReviewsOfInstaResponse of(List<BasicViewResponse> reviews, boolean hasNext){
        return CollectReviewsOfInstaResponse.builder()
                .reviews(reviews)
                .hasNext(hasNext)
                .build();
    }
}
