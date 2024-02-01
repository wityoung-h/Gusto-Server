package com.umc.gusto.domain.route.controller;

import com.umc.gusto.domain.route.service.RouteServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("routes")
@RequiredArgsConstructor
public class RouteListController {

    private final RouteServiceImpl routeService;


    // 리스트 거리 조회
    @GetMapping("/{routeId}/order")
    public ResponseEntity<?> getRouteOrder(
            @PathVariable Long routeId)
    {
        return null;
    }

    // 루트 상세 조회
    @GetMapping("/{routeId}")
    public ResponseEntity<?> getRouteDetail(
            @PathVariable Long routeId
    ){
        return null;
    }
}
