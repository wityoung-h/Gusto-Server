package com.umc.gusto.domain.review.model.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class SearchFeedResponse {
    List<BasicViewResponse> reviews;

    public static SearchFeedResponse of(List<BasicViewResponse> reviews){
        return SearchFeedResponse.builder()
                .reviews(reviews)
                .build();
    }
}
