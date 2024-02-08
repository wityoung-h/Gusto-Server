package com.umc.gusto.domain.review.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ReviewCalViewRequest {
    @NotNull String date;
}
