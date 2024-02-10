package com.umc.gusto.domain.review.model.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CreateReviewRequest {
    @NotNull(message = "storeId는 null일 수 없습니다.")
    Long storeId;
    LocalDate visitedAt;
    String menuName;
    String hashTagId;
    @NotNull
    @DecimalMin(value = "0", message = "점수가 0보다 작을 수 없습니다.")
    @DecimalMax(value = "5", message = "점수가 5보다 클 수 없습니다.")
    Integer taste;
    @DecimalMin(value = "0", message = "점수가 0보다 작을 수 없습니다.")
    @DecimalMax(value = "5", message = "점수가 5보다 클 수 없습니다.")
    Integer spiciness;
    @DecimalMin(value = "0", message = "점수가 0보다 작을 수 없습니다.")
    @DecimalMax(value = "5", message = "점수가 5보다 클 수 없습니다.")
    Integer mood;
    @DecimalMin(value = "0", message = "점수가 0보다 작을 수 없습니다.")
    @DecimalMax(value = "5", message = "점수가 5보다 클 수 없습니다.")
    Integer toilet;
    @DecimalMin(value = "0", message = "점수가 0보다 작을 수 없습니다.")
    @DecimalMax(value = "5", message = "점수가 5보다 클 수 없습니다.")
    Integer parking;
    @Size(max=200, message = "내용은 200자를 초과할 수 없습니다.")
    String comment;
}
