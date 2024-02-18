package com.umc.gusto.domain.review.model.response;

import com.umc.gusto.domain.review.entity.Review;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class CalendarViewResponse { //TODO: 데모데이 후 원래 캘린더 뷰 화면으로 사용하기 위해서 만든 DTO
    Long reviewId;
    LocalDate visitedDate;
    String images;

    public static CalendarViewResponse of(Review review){
        return CalendarViewResponse.builder()
                .reviewId(review.getReviewId())
                .visitedDate(review.getVisitedAt())
                .images(review.getImg1())
                .build();
    }
}
