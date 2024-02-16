package com.umc.gusto.domain.route.service;

import com.umc.gusto.domain.group.entity.Group;
import com.umc.gusto.domain.group.repository.GroupMemberRepository;
import com.umc.gusto.domain.group.repository.GroupRepository;
import com.umc.gusto.domain.route.entity.Route;
import com.umc.gusto.domain.route.entity.RouteList;
import com.umc.gusto.domain.route.model.request.ModifyRoueListRequest;
import com.umc.gusto.domain.route.model.request.ModifyRouteRequest;
import com.umc.gusto.domain.route.model.request.RouteListRequest;
import com.umc.gusto.domain.route.model.response.RouteListResponse;
import com.umc.gusto.domain.route.repository.RouteListRepository;
import com.umc.gusto.domain.route.repository.RouteRepository;
import com.umc.gusto.domain.store.entity.Store;
import com.umc.gusto.domain.store.repository.StoreRepository;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.global.common.BaseEntity;
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


    @Transactional
    @Override
    public void createRouteList(Route route, List<RouteListRequest.createRouteListDto> request) {
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
    public void createRouteList(Long groupId,Long routeId, List<RouteListRequest.createRouteListDto> request,User user) {
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
        RouteList routeList = routeListRepository.findById(routeListId).orElseThrow(() -> new NotFoundException(Code.ROUTELIST_NOT_FOUND));
        routeListRepository.delete(routeList);

    }

    @Override
    public List<RouteListResponse.RouteList> getRouteListDistance(Long routeId) {
        Route route = routeRepository.findRouteByRouteIdAndStatus(routeId,BaseEntity.Status.ACTIVE).orElseThrow(()-> new GeneralException(Code.ROUTE_NOT_FOUND));
        List<RouteList> routeList = routeListRepository.findByRoute(route);
        return  routeList.stream().map(rL ->
                RouteListResponse.RouteList.builder()
                        .longitude(rL.getStore().getLongitude())
                        .latitude(rL.getStore().getLatitude())
                        .routeListId(rL.getRouteListId())
                        .ordinal(rL.getOrdinal())
                        .storeId(rL.getStore().getStoreId())
                        .build()
        ).toList();

    }

    @Override
    public RouteListResponse.RouteListResponseDto getRouteListDetail(Long routeId,User user, Long groupId) {
        // 그룹 내 루트 상세 조회인 경우에만
        if(groupId != null){
            Group group = groupRepository.findGroupByGroupIdAndStatus(groupId, BaseEntity.Status.ACTIVE)
                    .orElseThrow(() -> new GeneralException(Code.FIND_FAIL_GROUP));
            if(!groupMemberRepository.existsGroupMemberByGroupAndUser(group,user)){
                throw new GeneralException(Code.USER_NOT_IN_GROUP);
            }
        }


        Route route = routeRepository.findRouteByRouteIdAndStatus(routeId, BaseEntity.Status.ACTIVE).orElseThrow(()-> new GeneralException(Code.ROUTE_NOT_FOUND));
        List<RouteList> routeList = routeListRepository.findByRoute(route);
        List<RouteListResponse.RouteList> routeLists = routeList.stream().map(rL ->
                RouteListResponse.RouteList.builder()
                        .routeListId(rL.getRouteListId())
                        .storeId(rL.getStore().getStoreId())
                        .storeName(rL.getStore().getStoreName())
                        .address(rL.getStore().getAddress())
                        .ordinal(rL.getOrdinal())
                        .build()
        ).toList();

        return RouteListResponse.RouteListResponseDto.builder()
                .routeId(route.getRouteId())
                .routeName(route.getRouteName())
                .routes(routeLists)
                .build();

    }

    @Transactional
    @Override
    public void modifyRouteList(Long routeId, ModifyRouteRequest request) {
        // 루트리스트 갯수가 6개 이하인지 확인
        // TODO: 루트 리스트.size()가 null 일때,즉 입력 시 루트리스트가 없을 때
        if (request.getRouteList().size() >= 7) {
            throw new GeneralException(Code.ROUTELIST_TO_MANY_REQUEST);
        }

        // 루트 존재 여부 확인
        Route route = routeRepository.findRouteByRouteIdAndStatus(routeId, BaseEntity.Status.ACTIVE)
                .orElseThrow(() -> new GeneralException(Code.ROUTE_NOT_FOUND));

        // 루트 이름 변경 확인
        if (request.getRouteName() != null) {
            route.updateRouteName(request.getRouteName());
        }

        for (ModifyRoueListRequest modifyRoueListRequest : request.getRouteList()) {
            // 변경된 PK값 확인
            if (modifyRoueListRequest.getRouteListId() != null) {
                // 해당 루트리스트 조회
                RouteList routeList = routeListRepository.findById(modifyRoueListRequest.getRouteListId())
                        .orElseThrow(() -> new GeneralException(Code.ROUTELIST_NOT_FOUND));

                // 루트리스트 내 수정된 컬럼 값만 업데이트 진행
                if (modifyRoueListRequest.getOrdinal() != null) {
                    routeList.updateOrdinal(modifyRoueListRequest.getOrdinal());
                }
                if (modifyRoueListRequest.getStoreId() != null) {
                    Store store = storeRepository.findById(modifyRoueListRequest.getStoreId())
                            .orElseThrow(() -> new GeneralException(Code.STORE_NOT_FOUND));
                    routeList.updateStore(store);
                }
            }
        }
    }


}
