package com.umc.gusto.domain.route.service;

import com.umc.gusto.domain.route.model.request.RouteRequest;
import com.umc.gusto.domain.route.model.response.RouteResponse;
import com.umc.gusto.domain.user.entity.User;

import java.util.List;

public interface RouteService {
    // 루트 생성
    void createRoute(RouteRequest.createRouteDto request,User user);

    // 루트 삭제
    void deleteRoute(Long routeId,User user);

    // 내 루트 조회
    List<RouteResponse.RouteResponseDto> getRoute(User user);

    // 그룹 내 루트 조회
    List<RouteResponse.RouteResponseDto> getGroupRoute(Long groupId );
}
