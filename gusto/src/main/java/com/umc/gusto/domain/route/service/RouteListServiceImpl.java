package com.umc.gusto.domain.route.service;

import com.umc.gusto.domain.route.entity.Route;
import com.umc.gusto.domain.route.entity.RouteList;
import com.umc.gusto.domain.route.model.request.RouteListRequest;
import com.umc.gusto.domain.route.repository.RouteListRepository;
import com.umc.gusto.domain.store.repository.StoreRepository;
import com.umc.gusto.global.exception.Code;
import com.umc.gusto.global.exception.customException.NotFoundException;
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
        request.forEach(dto -> {
            RouteList routeList = RouteList.builder()
                    .route(route)
                    .store(storeRepository.findById(dto.getStoreId())
                            .orElseThrow(() -> new NotFoundException(Code.STORE_NOT_FOUND)))
                    .ordinal(dto.getOrdinal())
                    .build();
            routeListRepository.save(routeList);
        });
    }

    @Override
    public void deleteRouteList(Long routeListId) {
        RouteList routeList = routeListRepository.findById(routeListId).orElseThrow(() -> new NotFoundException(Code.ROUTELIST_NOT_FOUND));
        routeListRepository.delete(routeList);

    }

}
