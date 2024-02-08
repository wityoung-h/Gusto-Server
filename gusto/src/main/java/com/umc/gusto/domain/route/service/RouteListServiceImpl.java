package com.umc.gusto.domain.route.service;

import com.umc.gusto.domain.route.entity.Route;
import com.umc.gusto.domain.route.entity.RouteList;
import com.umc.gusto.domain.route.model.request.RouteListRequest;
import com.umc.gusto.domain.route.model.response.RouteListResponse;
import com.umc.gusto.domain.route.repository.RouteListRepository;
import com.umc.gusto.domain.route.repository.RouteRepository;
import com.umc.gusto.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteListServiceImpl implements RouteListService{
    private final RouteListRepository routeListRepository;
    private final RouteRepository routeRepository;
    private final StoreRepository storeRepository;


    @Override
    public void createRouteList(Route route, List<RouteListRequest.createRouteListDto> request) {
        //루트리스트 생성
        request.forEach(dto -> {
                    RouteList routeList = RouteList.builder()
                            .route(route)
                            .store(storeRepository.findById(dto.getStoreId())
                                    .orElseThrow(() -> new RuntimeException("등록되어 있지 않은 가게입니다.")))
                            .ordinal(dto.getOrdinal())
                            .build();
            routeListRepository.save(routeList);
        });
    }

    @Override
    public void deleteRouteList(Long routeListId) {
        RouteList routeList = routeListRepository.findById(routeListId).orElseThrow(() -> new RuntimeException("저장되어 있지 않은 항목입니다."));
        routeListRepository.delete(routeList);

    }

    @Override
    public List<RouteListResponse.RouteList> getRouteListDistance(Long routeId) {
        Route route = routeRepository.findRouteByRouteId(routeId).get();
        List<RouteList> routeList = routeListRepository.findAllByRoute(route);
        return  routeList.stream().map(rL ->
                RouteListResponse.RouteList.builder()
                        .longtitude(rL.getStore().getLongtitude())
                        .latitude(rL.getStore().getLatitude())
                        .routeListId(rL.getRouteListId())
                        .ordinal(rL.getOrdinal())
                        .build()
        ).toList();

    }

    @Override
    public RouteListResponse.RouteListResponseDto getRouteListDetail(Long routeId) {
        Route route = routeRepository.findRouteByRouteId(routeId).get();
        List<RouteList> routeList = routeListRepository.findAllByRoute(route);
        List<RouteListResponse.RouteList> routeLists = routeList.stream().map(rL ->
                RouteListResponse.RouteList.builder()
                        .longtitude(rL.getStore().getLongtitude())
                        .latitude(rL.getStore().getLatitude())
                        .routeListId(rL.getRouteListId())
                        .ordinal(rL.getOrdinal())
                        .build()
        ).toList();

        return RouteListResponse.RouteListResponseDto.builder()
                .routeName(route.getRouteName())
                .routes(routeLists)
                .build();
    }

}
