package com.umc.gusto.domain.route.service;

import com.umc.gusto.domain.group.entity.Group;
import com.umc.gusto.domain.group.repository.GroupRepository;
import com.umc.gusto.domain.route.entity.Route;
import com.umc.gusto.domain.route.model.request.RouteRequest;
import com.umc.gusto.domain.route.model.response.RouteResponse;
import com.umc.gusto.domain.route.repository.RouteListRepository;
import com.umc.gusto.domain.route.repository.RouteRepository;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.domain.user.repository.UserRepository;
import com.umc.gusto.global.common.BaseEntity;
import com.umc.gusto.global.exception.Code;
import com.umc.gusto.global.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RouteServiceImpl implements RouteService{
    private final RouteRepository routeRepository;
    private final GroupRepository groupRepository;
    private final RouteListRepository routeListRepository;
    private final RouteListService routeListService;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public void createRoute(RouteRequest.createRouteDto request,User user) {
        // 루트명은 내 루트명 중에서 중복 불가능
        if (routeRepository.existsByRouteName(request.getRouteName(),BaseEntity.Status.ACTIVE,user)) {
            throw new GeneralException(Code.ROUTE_DUPLICATE_ROUTENAME);
        }
        // 루트 리스트 갯수 6개 제한 확인
        if(request.getRouteList().size()>=7){
            throw new GeneralException(Code.ROUTELIST_TO_MANY_REQUEST);
        }

        // 루트 생성
        Route route = Route.builder()
                .routeName(request.getRouteName())
                .user(user)
                .group(groupRepository.findGroupByGroupIdAndStatus(request.getGroupId(), BaseEntity.Status.ACTIVE).orElse(null))
                .build();
        Route savedRoute = routeRepository.save(route);

      
        if(request.getRouteList() != null){
            // 루트리스트 생성 비지니스 로직 호출
            routeListService.createRouteList(savedRoute, request.getRouteList());
        }else if(request.getGroupId() == null ){
            // 내 루트 생성시에는 최소 1개 이상의 경로가 포함되어야 함
            throw new GeneralException(Code.ROUTE_MYROUTE_BAD_REQUEST);
        }
    }

    @Transactional
    @Override
    public void deleteRoute(Long routeId, User user) {
        // 그룹X, ACTIVE 한정
        Route route = routeRepository.findRouteByRouteIdAndStatusAndGroup(routeId,BaseEntity.Status.ACTIVE)
                .orElseThrow(() -> new GeneralException(Code.ROUTE_NOT_FOUND));

        // 루트를 생성한 유저만 삭제
        if(!route.getUser().getNickname().equals(user.getNickname())){
            throw new GeneralException(Code.USER_NO_PERMISSION_FOR_ROUTE);
        }

        //루트 삭제 : soft delete // TODO:DB 최종 삭제 주기 체크
        route.updateStatus(BaseEntity.Status.INACTIVE);

    }

    @Override
    public List<RouteResponse.RouteResponseDto> getRoute(User user) {
        //TODO 유저 관련 에러처리 추가하기
        userRepository.findByNicknameAndMemberStatusIs(user.getNickname(), User.MemberStatus.ACTIVE)
                .orElseThrow(()->new GeneralException(Code.DONT_EXIST_USER));
        //TODO: 유저 활성화 설정 반영

        List<Route> routes = routeRepository.findRouteByUserAndStatus(user, BaseEntity.Status.ACTIVE);
        return routes.stream().map(
                        Route -> RouteResponse.RouteResponseDto.builder()
                                .routeId(Route.getRouteId())
                                .routeName(Route.getRouteName())
                                .numStore(routeListRepository.countRouteListByRoute(Route))
                                .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<RouteResponse.RouteResponseDto> getGroupRoute(Long groupId) {
        // 그룹 존재 여부 확인
        Group group = groupRepository.findGroupByGroupIdAndStatus(groupId, BaseEntity.Status.ACTIVE)
                .orElseThrow(()->new GeneralException(Code.FIND_FAIL_GROUP));

        //특정 그룹 내 루트 조회
        List<Route> routes = routeRepository.findRoutesByGroupAndStatus(group,BaseEntity.Status.ACTIVE);
        return routes.stream().map(
                        Route -> RouteResponse.RouteResponseDto.builder()
                                .routeId(Route.getRouteId())
                                .routeName(Route.getRouteName())
                                .numStore(routeListRepository.countRouteListByRoute(Route))
                                .groupId(Route.getRouteId())
                                .build())
                .collect(Collectors.toList());
    }


}
