package com.umc.gusto.domain.route.service;

import com.umc.gusto.domain.route.entity.Route;
import com.umc.gusto.domain.route.entity.RouteList;
import com.umc.gusto.domain.route.model.request.RouteListRequest;
import com.umc.gusto.domain.route.model.request.RouteRequest;
import com.umc.gusto.domain.route.repository.RouteListRepository;
import com.umc.gusto.domain.store.repository.StoreRepository;
import com.umc.gusto.domain.user.entity.User;
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
    private final StoreRepository storeRepository;


    @Transactional
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

    @Transactional
    @Override
    public void deleteRouteList(Long routeListId, User user) {
        RouteList routeList = routeListRepository.findById(routeListId).orElseThrow(() -> new NotFoundException(Code.ROUTELIST_NOT_FOUND));
        routeListRepository.delete(routeList);

    }

    @Override
    public void modifyRouteList(RouteRequest.createRouteDto request) {
        // 루트리스트 갯수가 6개 이하인지 확인
        if(request.getRouteList().size()>=7){
            throw new GeneralException(Code.ROUTELIST_TO_MANY_REQUEST);
        }
        // 루트리스트 중 변경된 PK값을 확인

        // 수정된 PK값만 업데이트 진행

        // 루트리스트 내 수정된 컬럼 값만 업데이트 진행
    }

}
