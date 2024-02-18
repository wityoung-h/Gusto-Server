package com.umc.gusto.domain.route.service;

import com.umc.gusto.domain.route.model.request.ModifyRouteRequest;
import com.umc.gusto.domain.route.model.request.RouteRequest;
import com.umc.gusto.domain.route.model.response.RouteResponse;
import com.umc.gusto.domain.user.entity.User;

import java.util.List;

public interface RouteService {
    // 루트 생성
    void createRoute(RouteRequest request,User user);

    // 루트 삭제
    void deleteRoute(Long routeId,User user);

    // 내 루트 조회
    List<RouteResponse> getRoute(User user);

    // 그룹 내 루트 조회
    List<RouteResponse> getGroupRoute(Long groupId );

    // 루트 상세 수정
    void modifyRouteList(Long routeId, ModifyRouteRequest request);

    // 타인의 루트 조회
    List<RouteResponse> getRoute(String nickname);
}
