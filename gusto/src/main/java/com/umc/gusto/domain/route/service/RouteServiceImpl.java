package com.umc.gusto.domain.route.service;

import com.umc.gusto.domain.group.repository.GroupRepository;
import com.umc.gusto.domain.route.entity.Route;
import com.umc.gusto.domain.route.entity.RouteList;
import com.umc.gusto.domain.route.model.request.RouteRequest;
import com.umc.gusto.domain.route.model.response.RouteResponse;
import com.umc.gusto.domain.route.repository.RouteListRepository;
import com.umc.gusto.domain.route.repository.RouteRepository;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.domain.user.repository.UserRepository;
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
    private final UserRepository userRepository;

    @Override
    public void createRoute(RouteRequest.createRouteDto request) {
        // 루트명은 내 루트명 중에서 중복 불가능
        if (routeRepository.existsByRouteName(request.getRouteName())) {
            throw new RuntimeException("루트명은 중복 불가로 입력하신 루트명은 이미 사용중인 루트명입니다.");
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
        Route route = routeRepository.findById(routeId).orElseThrow(() -> new RuntimeException("존재하지 않는 루트입니다."));
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
    public List<RouteResponse.RouteResponseDto> getRoute(String nickname) {
        User user = userRepository.findByNicknameAndMemberStatus(nickname, User.MemberStatus.ACTIVE)
                .orElseThrow(()-> new RuntimeException("존재하지 않는 회원입니다."));

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
