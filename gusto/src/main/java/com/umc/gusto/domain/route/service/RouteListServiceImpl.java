package com.umc.gusto.domain.route.service;

import com.umc.gusto.domain.route.entity.Route;
import com.umc.gusto.domain.route.entity.RouteList;
import com.umc.gusto.domain.route.model.request.RouteListRequest;
import com.umc.gusto.domain.route.repository.RouteListRepository;
import com.umc.gusto.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteListServiceImpl implements RouteListService{
    private final RouteListRepository routeListRepository;
    private final StoreRepository storeRepository;


    @Override
    public void createRouteList(Route route, List<RouteListRequest.createRouteListDto> request) {
        //루트리스트 생성
        RouteList routeList = RouteList.builder()
                .route(route)
                .store(storeRepository.findById(request.get(0).getStoreId()).orElseThrow(()-> new RuntimeException("등록되어 있지 않은 가게입니다.")))
                .build();

        routeListRepository.save(routeList);
    }

    @Override
    public void deleteRouteList(Long routeListId) {
        RouteList routeList = routeListRepository.findById(routeListId).orElseThrow(() -> new RuntimeException("저장되어 있지 않은 항목입니다."));
        routeListRepository.delete(routeList);

    }

}
