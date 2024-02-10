package com.umc.gusto.domain.review.model.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CalendarViewResponse { //TODO: 데모데이 후 원래 캘린더 뷰 화면으로 사용하기 위해서 만든 DTO
    Long reviewId;
    List<String> images;
    int month;
}
