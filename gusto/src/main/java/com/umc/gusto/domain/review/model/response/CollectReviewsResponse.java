package com.umc.gusto.domain.review.model.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CollectReviewsResponse {
    List<?> reviews;
    Long cursorId;
    boolean hasNext;

    public static CollectReviewsResponse of(List<?> reviews, Long cursorId, boolean hasNext){
        return CollectReviewsResponse.builder()
                .reviews(reviews)
                .cursorId(cursorId)
                .hasNext(hasNext)
                .build();
    }
}
