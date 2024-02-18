package com.umc.gusto.domain.route.service;

import com.umc.gusto.domain.group.entity.Group;
import com.umc.gusto.domain.group.repository.GroupMemberRepository;
import com.umc.gusto.domain.group.repository.GroupRepository;
import com.umc.gusto.domain.route.entity.Route;
import com.umc.gusto.domain.route.entity.RouteList;
import com.umc.gusto.domain.route.model.request.RouteListRequest;
import com.umc.gusto.domain.route.model.response.RouteListResponse;
import com.umc.gusto.domain.route.model.response.RouteRouteListResponse;
import com.umc.gusto.domain.route.repository.RouteListRepository;
import com.umc.gusto.domain.route.repository.RouteRepository;
import com.umc.gusto.domain.store.repository.StoreRepository;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.domain.user.repository.UserRepository;
import com.umc.gusto.global.common.BaseEntity;
import com.umc.gusto.global.common.PublishStatus;
import com.umc.gusto.global.exception.Code;
import com.umc.gusto.global.exception.GeneralException;
import com.umc.gusto.global.exception.customException.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RouteListServiceImpl implements RouteListService{
    private final RouteListRepository routeListRepository;
    private final RouteRepository routeRepository;
    private final StoreRepository storeRepository;
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final UserRepository userRepository;


    @Transactional
    @Override
    public void createRouteList(Route route, List<RouteListRequest> request) {
        //루트리스트 생성
        request.forEach(dto -> {
            if(dto.getOrdinal() >= 1 && dto.getOrdinal() <= 6){
                RouteList routeList = RouteList.builder()
                        .route(route)
                        .store(storeRepository.findById(dto.getStoreId())
                                .orElseThrow(() -> new GeneralException(Code.STORE_NOT_FOUND)))
                        .ordinal(dto.getOrdinal())
                        .build();
                routeListRepository.save(routeList);
            } else throw new GeneralException(Code.ROUTE_ORDINAL_BAD_REQUEST);
        });
    }

    // 루트리스트만 추가
    @Transactional
    @Override
    public void createRouteList(Long groupId,Long routeId, List<RouteListRequest> request,User user) {
        if(groupId != null){
            Group group = groupRepository.findGroupByGroupIdAndStatus(groupId, BaseEntity.Status.ACTIVE)
                    .orElseThrow(() -> new GeneralException(Code.FIND_FAIL_GROUP));
            if(!groupMemberRepository.existsGroupMemberByGroupAndUser(group,user)){
                throw new GeneralException(Code.USER_NOT_IN_GROUP);
            }
        }

        //루트리스트 생성
        request.forEach(dto -> {
            RouteList routeList = RouteList.builder()
                    .route(routeRepository.findRouteByRouteIdAndStatus(routeId, BaseEntity.Status.ACTIVE)
                            .orElseThrow(()-> new GeneralException(Code.ROUTE_NOT_FOUND)))
                    .store(storeRepository.findById(dto.getStoreId())
                            .orElseThrow(() -> new GeneralException(Code.STORE_NOT_FOUND)))
                    .ordinal(dto.getOrdinal())
                    .build();
            routeListRepository.save(routeList);
        });
    }

    @Transactional
    @Override
    public void deleteRouteList(Long routeListId, User user) {
        RouteList deleteRouteList = routeListRepository.findById(routeListId).orElseThrow(() -> new NotFoundException(Code.ROUTELIST_NOT_FOUND));
        routeListRepository.delete(deleteRouteList);

        Route route = routeRepository.findRouteByRouteIdAndStatus(deleteRouteList.getRoute().getRouteId(), BaseEntity.Status.ACTIVE)
                .orElseThrow(()-> new GeneralException(Code.ROUTE_NOT_FOUND));

        List<RouteList> routeLists = routeListRepository.findByRouteOrderByOrdinalAsc(route);
        for (RouteList list : routeLists) {
            // 삭제된 아이템의 순서보다 큰 아이템들의 순서를 조정
            if(deleteRouteList.getOrdinal()< list.getOrdinal() && list.getOrdinal() >1)
                list.updateOrdinal(list.getOrdinal()-1);

        }
    }


    @Override
    public List<RouteListResponse> getRouteListDistance(Long routeId) {
        Route route = routeRepository.findRouteByRouteIdAndStatus(routeId,BaseEntity.Status.ACTIVE).orElseThrow(()-> new GeneralException(Code.ROUTE_NOT_FOUND));
        List<RouteList> routeList = routeListRepository.findByRouteOrderByOrdinalAsc(route);
        return  routeList.stream().map(rL ->
                RouteListResponse.builder()
                        .longitude(rL.getStore().getLongitude())
                        .latitude(rL.getStore().getLatitude())
                        .routeListId(rL.getRouteListId())
                        .ordinal(rL.getOrdinal())
                        .storeId(rL.getStore().getStoreId())
                        .build()
        ).toList();

    }

    @Override
    public RouteRouteListResponse getRouteListDetail(Long routeId, User user, Long groupId,String nickname) {
        // 그룹 내 루트 상세 조회인 경우에만
        if(groupId != null){
            Group group = groupRepository.findGroupByGroupIdAndStatus(groupId, BaseEntity.Status.ACTIVE)
                    .orElseThrow(() -> new GeneralException(Code.FIND_FAIL_GROUP));
            if(!groupMemberRepository.existsGroupMemberByGroupAndUser(group,user)){
                throw new GeneralException(Code.USER_NOT_IN_GROUP);
            }
        }

        // 타인 조회 시
        if(nickname != null){
            User other = userRepository.findByNicknameAndMemberStatusIs(nickname, User.MemberStatus.ACTIVE).orElseThrow(()-> new NotFoundException(Code.USER_NOT_FOUND));
            //다른 유저의 공개 여부 확인
            if(!other.getPublishReview().equals(PublishStatus.PUBLIC)){
                throw new GeneralException(Code.NO_PUBLIC_ROUTE);
            }
        }


        // 조회 공통 로직
        Route route = routeRepository.findRouteByRouteIdAndStatus(routeId, BaseEntity.Status.ACTIVE).orElseThrow(()-> new GeneralException(Code.ROUTE_NOT_FOUND));

        List<RouteList> routeList = routeListRepository.findByRouteOrderByOrdinalAsc(route);
        List<RouteListResponse> routeLists = routeList.stream().map(rL ->
                RouteListResponse.builder()
                        .routeListId(rL.getRouteListId())
                        .storeId(rL.getStore().getStoreId())
                        .storeName(rL.getStore().getStoreName())
                        .address(rL.getStore().getAddress())
                        .ordinal(rL.getOrdinal())
                        .build()
        ).toList();

        return RouteRouteListResponse.builder()
                .routeId(route.getRouteId())
                .routeName(route.getRouteName())
                .routes(routeLists)
                .build();

    }


}
