package com.umc.gusto.domain.route.service;

import com.umc.gusto.domain.route.model.request.RouteRequest;
import com.umc.gusto.domain.route.model.response.RouteResponse;

import java.util.List;

public interface RouteService {
    // 루트 생성
    void createRoute(RouteRequest.createRouteDto request);

    // 루트 삭제
    void deleteRoute(Long routeId);

    // 내 루트 조회
    List<RouteResponse.RouteResponseDto> getRoute(String nickname);

}
