package com.umc.gusto.domain.route.service;

import com.umc.gusto.domain.route.model.request.RouteRequest;

public interface RouteService {
    // 루트 생성
    void createRoute(RouteRequest.createRouteDto request);

    // 루트 삭제
    void  deleteRoute(Long routeId);

    // 루트 리스트 조회

    // 리스트 거리 조회

    // 루트 상세 조회
}
