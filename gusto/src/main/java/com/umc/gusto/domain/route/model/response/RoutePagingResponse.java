package com.umc.gusto.domain.route.model.response;

import lombok.Builder;
import lombok.Setter;

import java.util.List;

@Setter
@Builder
public class RoutePagingResponse {
    private Boolean hasNest;
    private List<?> result;
}
