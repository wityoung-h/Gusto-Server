package com.umc.gusto.domain.review.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ReviewCalViewRequest {
    @Pattern(regexp = "^[\\d]{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$", message = "날짜 형식이 맞지않습니다.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") //TODO: 날짜 형식이 다를 경우에 대한 유효성 검증을 하고 싶지만 pattern과 jsonformat이 제대로 되지 않음, 나중에 고치기
    @NotNull LocalDate date;
}
