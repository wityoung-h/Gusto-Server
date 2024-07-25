package com.umc.gusto.domain.review.model.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class UpdateReviewRequest {
    LocalDate visitedAt;
    String menuName;
    List<Long> hashTagId;
    @DecimalMin(value = "0", message = "점수가 0보다 작을 수 없습니다.")
    @DecimalMax(value = "5", message = "점수가 5보다 클 수 없습니다.")
    Integer taste;
    @Size(max=200, message = "내용은 200자를 초과할 수 없습니다.")
    String comment;
    Boolean publicCheck; //Boolean box화한 기본 자료형을 사용하여 null이 들어올 수 있도록 한다.
}
