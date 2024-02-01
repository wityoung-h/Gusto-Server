package com.umc.gusto.domain.route.service;

import com.umc.gusto.domain.route.entity.Route;
import com.umc.gusto.domain.route.entity.RouteList;
import com.umc.gusto.domain.route.model.request.RouteListRequest;

import java.util.List;
import java.util.Optional;

public interface RouteListService {

    // 루트 리스트 생성
    void createRouteList(Route route, List<RouteListRequest.createRouteListDto> request);

    // 루트 리스트 항목 삭제
    void deleteRouteList(Long routeListId);

}
