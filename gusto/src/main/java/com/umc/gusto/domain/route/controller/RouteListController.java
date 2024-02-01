package com.umc.gusto.domain.route.controller;

import com.umc.gusto.domain.route.service.RouteListServiceImpl;
import com.umc.gusto.domain.route.service.RouteServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("routes")
@RequiredArgsConstructor
public class RouteListController {

    private final RouteListServiceImpl routeListService;

    // 루르리스트 항목 삭제
    @DeleteMapping()
    public ResponseEntity<?> deleteRouteLis(
            @PathVariable Long routeListId
    ){
        routeListService.deleteRouteList(routeListId);
        return ResponseEntity.ok().build();
    }


    // 루트리스트 간 거리 조회
    @GetMapping("/{routeId}/order")
    public ResponseEntity<?> getRouteListOrder(
            @PathVariable Long routeId)
    {
        return null;
    }

    // 루트리스트 상세 조회
    @GetMapping("/{routeId}")
    public ResponseEntity<?> getRouteListDetail(
            @PathVariable Long routeId
    ){
        return null;
    }


}
