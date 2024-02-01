package com.umc.gusto.domain.route.service;

import com.umc.gusto.domain.group.repository.GroupRepository;
import com.umc.gusto.domain.route.entity.Route;
import com.umc.gusto.domain.route.entity.RouteList;
import com.umc.gusto.domain.route.model.request.RouteRequest;
import com.umc.gusto.domain.route.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService{
    private final RouteRepository routeRepository;
    private final GroupRepository groupRepository;

    private final RouteListServiceImpl routeListService;

    @Override
    public void createRoute(RouteRequest.createRouteDto request) {
        //루트명은 내 루트명 중에서 중복 불가능
        if(routeRepository.findRouteByRouteName(request.getRouteName())){
            //루트 생성
            Route route = Route.builder()
                    .routeName(request.getRouteName())
                    .group(groupRepository.findGroupByGroupId(request.getGroupId()).orElseThrow(() -> new RuntimeException("존재하지 않는 그룹입니다.")))
                    .build();
            routeRepository.save(route);

            //루트리스트 생성 비지니스 로직 호출
            routeListService.createRouteList(route,request.getRouteList());
        }

    }

    @Override
    public void deleteRoute(Long routeId) {

    }




}
