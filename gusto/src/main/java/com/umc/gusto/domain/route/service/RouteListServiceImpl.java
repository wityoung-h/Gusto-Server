package com.umc.gusto.domain.route.service;

import com.umc.gusto.domain.route.entity.Route;
import com.umc.gusto.domain.route.entity.RouteList;
import com.umc.gusto.domain.route.model.request.RouteListRequest;
import com.umc.gusto.domain.route.model.response.RouteListResponse;
import com.umc.gusto.domain.route.repository.RouteListRepository;
import com.umc.gusto.domain.route.repository.RouteRepository;
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


    @Transactional
    @Override
    public void createRouteList(Route route, List<RouteListRequest.createRouteListDto> request) {
        //루트리스트 생성
        request.forEach(dto -> {
            RouteList routeList = RouteList.builder()
                    .route(route)
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
                        .longtitude(rL.getStore().getLongitude())
                        .latitude(rL.getStore().getLatitude())
                        .routeListId(rL.getRouteListId())
                        .ordinal(rL.getOrdinal())
                        .build()
        ).toList();

    }

    @Override
    public RouteListResponse.RouteListResponseDto getRouteListDetail(Long routeId) {
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
                .routeName(route.getRouteName())
                .routes(routeLists)
                .build();
    }

}
