package com.umc.gusto.domain.route.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.List;

public class RouteRequest {

    @Getter
    public static class createRouteDto{

        @NotBlank(message = "루트 명은 필수 입력값입니다.")
        private String routeName;
        private Long groupId;

        private List<RouteListRequest.createRouteListDto> routeList;

    }


}
