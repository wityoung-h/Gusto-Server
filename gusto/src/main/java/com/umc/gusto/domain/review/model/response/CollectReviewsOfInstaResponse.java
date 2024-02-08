package com.umc.gusto.domain.review.model.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CollectReviewsOfInstaResponse {
    List<InstaViewResponse> reviews;
    boolean hasNext;

    public static CollectReviewsOfInstaResponse of(List<InstaViewResponse> reviews, boolean hasNext){
        return CollectReviewsOfInstaResponse.builder()
                .reviews(reviews)
                .hasNext(hasNext)
                .build();
    }
}
