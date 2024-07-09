package com.umc.gusto.domain.route.model.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RouteListRequest {
    @NotNull(message = "루트 작성 시 식당 입력은 필수입니다.")
    private Long storeId;

    @DecimalMin(value = "1", message = "이동 순서가 1보다 작을 수 없습니다.")
    @DecimalMax(value = "6", message = "이동 순서가 6보다 클 수 없습니다.")
    private Integer ordinal;
}
