package com.umc.gusto.domain.route.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RouteRouteListResponse {
    private Long routeId;
    private String routeName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean publishRoute;
    private List<RouteListResponse> routes;
}
