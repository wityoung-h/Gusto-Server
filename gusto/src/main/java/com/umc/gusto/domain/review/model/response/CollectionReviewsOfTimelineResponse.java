package com.umc.gusto.domain.review.model.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CollectionReviewsOfTimelineResponse {
    List<TimelineViewResponse> reviews;
    boolean hasNext;
}
