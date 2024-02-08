package com.umc.gusto.domain.review.model.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CollectionReviewsOfCalResponse {
    List<CalendarViewResponse> reviews;
    boolean hasNext;
}
