package com.umc.gusto.domain.review.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ReviewViewRequest {
    Long reviewId;
    @NotNull(message = "size는 null일 수 없습니다.")
    int size;
}
