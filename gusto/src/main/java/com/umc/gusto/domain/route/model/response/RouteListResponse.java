package com.umc.gusto.domain.route.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RouteListResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double longitude;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double latitude;
    private Long routeListId;
    private Integer ordinal;
    private Long storeId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String storeName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String address;
}
