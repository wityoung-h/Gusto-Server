package com.umc.gusto.domain.route.service;

import com.umc.gusto.domain.route.model.request.RouteRequest;
import com.umc.gusto.domain.route.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService{
    private final RouteRepository routeRepository;

    @Override
    public void createRoute(RouteRequest.createRouteDto request) {
        //루트명은 내 루트명 중에서 중복 불가능
        //루트 생성
        // 루트리스트 생성
    }

    @Override
    public void deleteRoute(Long routeId) {

    }




}
