package com.umc.gusto.domain.route.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

public class RouteResponse {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RouteResponseDto{
        private Long routeId;
        private String routeName;
        private int numStore;

    }

}
