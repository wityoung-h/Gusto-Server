package com.umc.gusto.domain.review.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ReviewViewRequest {
    Long reviewId;
    @NotNull
    int size;
}
