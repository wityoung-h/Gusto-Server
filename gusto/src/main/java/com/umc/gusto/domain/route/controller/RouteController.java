package com.umc.gusto.domain.route.controller;

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

    // 루트 생성
    @PostMapping("")
    public ResponseEntity<?> createRoute(
            @RequestBody RouteRequest.createRouteDto request,
            @AuthenticationPrincipal AuthUser authUSer)
    {
        routeService.createRoute(request,authUSer.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 루트 삭제
    @DeleteMapping("/{routeId}")
    public ResponseEntity<?> deleteRoute(
            @PathVariable Long routeId,
            @AuthenticationPrincipal AuthUser authUSer)
    {
        routeService.deleteRoute(routeId,authUSer.getUser());
        return ResponseEntity.ok().build();
    }

    // 내 루트 조회
    @GetMapping("")
    public ResponseEntity<List<RouteResponse.RouteResponseDto>> allMyRoute(
            @AuthenticationPrincipal AuthUser authUSer
    ){
        List<RouteResponse.RouteResponseDto> route = routeService.getRoute(authUSer.getUser());
        return ResponseEntity.ok().body(route);
    }

    // 그룹 내 루트 조회
    @GetMapping("/groups/{groupId}")
    public ResponseEntity<List<RouteResponse.RouteResponseDto>> allMyRoute(@PathVariable Long groupId){
        List<RouteResponse.RouteResponseDto> route = routeService.getGroupRoute(groupId);
        return ResponseEntity.ok().body(route);
    }





}
