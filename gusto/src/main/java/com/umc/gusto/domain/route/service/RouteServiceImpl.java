package com.umc.gusto.domain.route.service;

import com.umc.gusto.domain.group.repository.GroupRepository;
import com.umc.gusto.domain.route.entity.Route;
import com.umc.gusto.domain.route.entity.RouteList;
import com.umc.gusto.domain.route.model.request.RouteRequest;
import com.umc.gusto.domain.route.model.response.RouteResponse;
import com.umc.gusto.domain.route.repository.RouteListRepository;
import com.umc.gusto.domain.route.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService{
    private final RouteRepository routeRepository;
    private final GroupRepository groupRepository;
    private final RouteListRepository routeListRepository;
    private final RouteListServiceImpl routeListService;

    @Override
    public void createRoute(RouteRequest.createRouteDto request) {
        //루트명은 내 루트명 중에서 중복 불가능
        if(routeRepository.existsByRouteName(request.getRouteName())){
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
        Route route = routeRepository.findById(routeId).orElseThrow(() -> new RuntimeException("존재하지 않는 루트입니다."));
        //Route 삭제 시 RouteList 먼저 삭제
        List<RouteList> routeLists = routeListRepository.findAllByRoute(routeId);
        if(routeLists != null){
            List<Long> routeListIds = routeLists.stream().map(RouteList::getRouteListId).toList();
            routeListRepository.deleteAllById(routeListIds);
        }
        //루트 삭제
        routeRepository.delete(route);

    }

    @Override
    public List<RouteResponse.RouteResponseDto> getRoute() {
        return null;
    }


}
