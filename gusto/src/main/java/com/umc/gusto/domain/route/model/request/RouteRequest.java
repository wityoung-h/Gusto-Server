package com.umc.gusto.domain.route.model.request;

import com.umc.gusto.global.common.PublishStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.List;

@Getter
public class RouteRequest {
    @NotBlank(message = "루트 명은 필수 입력값입니다.")
    private String routeName;
    private Long groupId;
    private boolean publishRoute; // public =>true , private => false
    private List<RouteListRequest> routeList;

    //isPublishRoute 대신 직접 지정
    public PublishStatus getPublishRoute() {
        return this.publishRoute ? PublishStatus.PUBLIC: PublishStatus.PRIVATE;
    }
}
