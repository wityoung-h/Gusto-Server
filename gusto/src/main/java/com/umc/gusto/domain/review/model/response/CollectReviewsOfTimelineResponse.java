package com.umc.gusto.domain.review.model.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CollectReviewsOfTimelineResponse {
    List<TimelineViewResponse> reviews;
    boolean hasNext;

    public static CollectReviewsOfTimelineResponse of(List<TimelineViewResponse> reviews, boolean hasNext){
        return CollectReviewsOfTimelineResponse.builder()
                .reviews(reviews)
                .hasNext(hasNext)
                .build();
    }
}
