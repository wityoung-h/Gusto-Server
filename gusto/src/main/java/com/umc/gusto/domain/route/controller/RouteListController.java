package com.umc.gusto.domain.route.controller;

import com.umc.gusto.domain.route.model.request.ModifyRouteRequest;
import com.umc.gusto.domain.route.model.response.RouteListResponse;
import com.umc.gusto.domain.route.service.RouteListService;
import com.umc.gusto.global.auth.model.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("routeLists")
@RequiredArgsConstructor
public class RouteListController {

    private final RouteListService routeListService;

    // 루르리스트 항목 삭제
    @DeleteMapping("/{routeListId}")
    public ResponseEntity<?> deleteRouteLis(
            @PathVariable Long routeListId,
            @AuthenticationPrincipal AuthUser authUSer
    ){
        routeListService.deleteRouteList(routeListId,authUSer.getUser());
        return ResponseEntity.ok().build();
    }

    // 루트리스트 간 거리 조회
    @GetMapping("/{routeId}/order")
    public ResponseEntity<List<RouteListResponse.RouteList>> getRouteListOrder(
            @PathVariable Long routeId)
    {
        return ResponseEntity.ok().body(routeListService.getRouteListDistance(routeId));
    }

    // 루트리스트 상세 조회
    @GetMapping("/{routeId}")
    public ResponseEntity<?> getRouteListDetail(
            @PathVariable Long routeId
    ){
        return ResponseEntity.ok().body(routeListService.getRouteListDetail(routeId));
    }

    // 루트 수정
    @PatchMapping("/{routeId}")
    public ResponseEntity<?> modifyRoute(@PathVariable Long routeId, @RequestBody ModifyRouteRequest request){
        routeListService.modifyRouteList(routeId,request);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
