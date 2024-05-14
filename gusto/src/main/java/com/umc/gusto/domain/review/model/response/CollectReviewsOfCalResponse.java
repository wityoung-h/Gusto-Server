package com.umc.gusto.domain.review.model.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CollectReviewsOfCalResponse {
    List<CalendarViewResponse> reviews; //TODO: 데모데이 후 원래 캘린더 뷰 화면으로 사용할때는 BasicViewResponse가 아닌 CalendarViewReponse 사용하기
    //TODO: 데모데이 후 boolean hasNext 체크 하기

//    public static CollectReviewsOfCalResponse of(List<BasicViewResponse> reviews, boolean hasNext){
//        return CollectReviewsOfCalResponse.builder()
//                .reviews(reviews)
//                .build();
//    }
}
