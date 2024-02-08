package com.umc.gusto.domain.route.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

public class RouteListResponse {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RouteList{
        @JsonInclude()
        private float longtitude;
        @JsonInclude()
        private Float latitude;
        private Long routeListId;
        private Integer ordinal;

        @JsonInclude()
        private Long storeId;
        @JsonInclude()
        private String storeName;
        @JsonInclude()
        private String address;

}

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RouteListResponseDto{
        private String routeName;
        private List<RouteList> routes;
    }



}
