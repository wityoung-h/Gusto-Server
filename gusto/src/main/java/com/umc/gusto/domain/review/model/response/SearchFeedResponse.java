package com.umc.gusto.domain.review.model.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;



@Builder
@Getter
public class SearchFeedResponse {
    List<BasicViewResponse> reviews;
    boolean hasNext;
    Long cursorId;


    public static SearchFeedResponse of(List<BasicViewResponse> reviews, boolean hasNext, Long cursorId){
        return SearchFeedResponse.builder()
                .reviews(reviews)
                .hasNext(hasNext)
                .cursorId(cursorId)
                .build();
    }
}
