package com.umc.gusto.domain.route.controller;

import com.umc.gusto.domain.route.model.request.ModifyRouteRequest;
import com.umc.gusto.domain.route.model.request.RouteRequest;
import com.umc.gusto.domain.route.model.response.RouteResponse;
import com.umc.gusto.domain.route.service.RouteService;
import com.umc.gusto.global.auth.model.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("routes")
@RequiredArgsConstructor
public class RouteController {
    private final RouteService routeService;

    /**
     * 루트 생성/그룹 내 루트 추가
     * [POST] /routes
     */
    // 루트 생성
    @PostMapping("")
    public ResponseEntity<?> createRoute(
            @RequestBody RouteRequest request,
            @AuthenticationPrincipal AuthUser authUSer)
    {
        routeService.createRoute(request,authUSer.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 내 루트 삭제
     * [DELETE] /routes/{routeId}
     */
    @DeleteMapping("/{routeId}")
    public ResponseEntity<?> deleteRoute(
            @PathVariable Long routeId,
            @AuthenticationPrincipal AuthUser authUSer)
    {
        routeService.deleteRoute(routeId,authUSer.getUser());
        return ResponseEntity.ok().build();
    }

    /**
     * 내 루트 조회
     * [GET] /routes
     */
    @GetMapping("")
    public ResponseEntity<List<RouteResponse>> allMyRoute(
            @AuthenticationPrincipal AuthUser authUSer
    ){
        List<RouteResponse> route = routeService.getRoute(authUSer.getUser());
        return ResponseEntity.ok().body(route);
    }

    /**
     * 그룹 내 루트 목록
     * [GET] /routes/groups{groupId}
     */
    @GetMapping("/groups/{groupId}")
    public ResponseEntity<List<RouteResponse>> allMyRoute(@PathVariable Long groupId){
        List<RouteResponse> route = routeService.getGroupRoute(groupId);
        return ResponseEntity.ok().body(route);
    }

    /**
     * 루트 수정
     * /{routeId}
     */
    @PatchMapping("/{routeId}")
    public ResponseEntity<?> modifyRoute(@PathVariable Long routeId, @RequestBody ModifyRouteRequest request){
        routeService.modifyRouteList(routeId,request);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * 타인의 루트 조회
     * [GET] /routes/{nickname}
     */
    @GetMapping("/{nickname}")
    public ResponseEntity<List<RouteResponse>> allUserRoute(@PathVariable String nickname){
        List<RouteResponse> route = routeService.getRoute(nickname);
        return ResponseEntity.ok().body(route);
    }


}
