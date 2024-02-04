package com.umc.gusto.domain.route.service;

import com.umc.gusto.domain.group.repository.GroupRepository;
import com.umc.gusto.domain.route.entity.Route;
import com.umc.gusto.domain.route.entity.RouteList;
import com.umc.gusto.domain.route.model.request.RouteRequest;
import com.umc.gusto.domain.route.model.response.RouteResponse;
import com.umc.gusto.domain.route.repository.RouteListRepository;
import com.umc.gusto.domain.route.repository.RouteRepository;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.global.exception.Code;
import com.umc.gusto.global.exception.GeneralException;
import com.umc.gusto.global.exception.customException.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService{
    private final RouteRepository routeRepository;
    private final GroupRepository groupRepository;
    private final RouteListRepository routeListRepository;
    private final RouteListServiceImpl routeListService;

    @Override
    public void createRoute(RouteRequest.createRouteDto request) {
        // 루트명은 내 루트명 중에서 중복 불가능
        if (routeRepository.existsByRouteName(request.getRouteName())) {
            throw new GeneralException(Code.ROUTE_DUPLICATE_ROUTENAME);
        }

        // 루트 생성
        Route route = Route.builder()
                .routeName(request.getRouteName())
                .group(groupRepository.findGroupByGroupId(request.getGroupId()).orElse(null))
                .build();
        Route savedRoute = routeRepository.save(route);

        // 루트리스트 생성 비지니스 로직 호출
        routeListService.createRouteList(savedRoute, request.getRouteList());
    }

    @Override
    public void deleteRoute(Long routeId) {
        Route route = routeRepository.findById(routeId).orElseThrow(() -> new NotFoundException(Code.ROUTE_NOT_FOUND));
        //Route 삭제 시 RouteList 먼저 삭제
        List<RouteList> routeLists = routeListRepository.findAllByRoute(route);
        if(routeLists != null){
            List<Long> routeListIds = routeLists.stream().map(RouteList::getRouteListId).toList();
            routeListRepository.deleteAllById(routeListIds);
        }
        //루트 삭제
        routeRepository.delete(route);

    }

    @Override
    public List<RouteResponse.RouteResponseDto> getRoute(User user) {

        List<Route> routes = routeRepository.findRouteByUser(user);
        return routes.stream().map(
                        Route -> RouteResponse.RouteResponseDto.builder()
                                .routeId(Route.getRouteId())
                                .routeName(Route.getRouteName())
                                .numStore(routeListRepository.countRouteListByRoute(Route))
                                .build())
                .collect(Collectors.toList());
    }


}
