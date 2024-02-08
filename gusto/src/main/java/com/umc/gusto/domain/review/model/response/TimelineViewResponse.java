package com.umc.gusto.domain.review.model.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
public class TimelineViewResponse {
    Long reviewId;
    String storeName;
    LocalDate visitedAt;
    int visitedCount;
    List<String> images;
}
