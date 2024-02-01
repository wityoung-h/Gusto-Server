package com.umc.gusto.domain.review.model.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDate;

public class ReviewRequest {
    @Getter
    public static class createReviewDTO{
        @NotNull(message = "storeId는 null일 수 없습니다.")
        Long storeId;
        LocalDate visitedAt;
        //TODO: 이미지 부분은 S3보고 생각해봐야함
        String img;
        String menuName;
        String hashTagId;
        @DecimalMin(value = "0", message = "점수가 0보다 작을 수 없습니다.")
        @DecimalMax(value = "5", message = "점수가 5보다 클 수 없습니다.")
        int taste;
        @DecimalMin(value = "0", message = "점수가 0보다 작을 수 없습니다.")
        @DecimalMax(value = "5", message = "점수가 5보다 클 수 없습니다.")
        int spiciness;
        @DecimalMin(value = "0", message = "점수가 0보다 작을 수 없습니다.")
        @DecimalMax(value = "5", message = "점수가 5보다 클 수 없습니다.")
        int mood;
        @DecimalMin(value = "0", message = "점수가 0보다 작을 수 없습니다.")
        @DecimalMax(value = "5", message = "점수가 5보다 클 수 없습니다.")
        int toilet;
        @DecimalMin(value = "0", message = "점수가 0보다 작을 수 없습니다.")
        @DecimalMax(value = "5", message = "점수가 5보다 클 수 없습니다.")
        int parking;
        @Size(max=200, message = "내용은 200자를 초과할 수 없습니다.")
        String comment;
    }
}
