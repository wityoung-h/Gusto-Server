package com.umc.gusto.domain.route.controller;


import com.umc.gusto.domain.route.model.request.RouteListRequest;
import com.umc.gusto.domain.route.model.response.RouteListResponse;
import com.umc.gusto.domain.route.service.RouteListService;
import com.umc.gusto.global.auth.model.AuthUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    /**
     * 루트 내 식당만 추가 == 루트리스트만 추가
     * [POST] /routeLists/{routeId}
     */
    @PostMapping("/{routeId}")
    public ResponseEntity<?> createRouteList
    (@PathVariable Long routeId,
     @RequestParam(required = false) Long groupId,
     @RequestBody @Valid List<RouteListRequest> request,
     @AuthenticationPrincipal AuthUser authUSer
    ){
        routeListService.createRouteList(groupId,routeId,request,authUSer.getUser());
        return ResponseEntity.ok().build();
    }

    /**
     * 루트 내 식당 삭제 == 루트 내 경로 삭제
     * [DELETE] /routeLists/{routeId}
     */
    @DeleteMapping("/{routeListId}")
    public ResponseEntity<?> deleteRouteLis(
            @PathVariable Long routeListId,
            @AuthenticationPrincipal AuthUser authUSer
    ){
        routeListService.deleteRouteList(routeListId,authUSer.getUser());
        return ResponseEntity.ok().build();
    }

    /**
     * 내 루트/그룹 루트 간 거리 조회
     * [GET] /routeLists/{routeId}/order
     */
    @GetMapping("/{routeId}/order")
    public ResponseEntity<List<RouteListResponse>> getRouteListOrder(
            @PathVariable Long routeId)
    {
        return ResponseEntity.ok().body(routeListService.getRouteListDistance(routeId));
    }

    /**
     * 내 루트/그룹 루트 상세 조회
     * [GET] /routeLists/{routeId}
     */
    //
    @GetMapping("/{routeId}")
    public ResponseEntity<?> getRouteListDetail(
            @PathVariable Long routeId,
            @RequestParam(required = false) Long groupId,
            @AuthenticationPrincipal AuthUser authUSer
    ){
        return ResponseEntity.ok().body(routeListService.getRouteListDetail(routeId,authUSer.getUser(),groupId));
    }

    /**
     * 타인의 루트 상세 조회
     * [GET] /routeLists/{routeId}?nickname={nickname}
     */
    @GetMapping("/{routeId}")
    public ResponseEntity<?> getRouteListDetail(
            @PathVariable Long routeId,
            @RequestParam String nickname
    ){
        return ResponseEntity.ok().body(routeListService.getRouteListDetail(routeId,nickname));
    }

}
