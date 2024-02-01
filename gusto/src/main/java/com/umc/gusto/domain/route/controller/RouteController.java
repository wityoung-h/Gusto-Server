package com.umc.gusto.domain.route.controller;

import com.umc.gusto.domain.route.model.request.RouteRequest;
import com.umc.gusto.domain.route.service.RouteServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("routes")
@RequiredArgsConstructor
public class RouteController {
    private final RouteServiceImpl routeService;

    // 루트 생성
    @PostMapping("")
    public ResponseEntity<?> create(
            @RequestBody @Valid RouteRequest.createRouteDto request){
    routeService.createRoute(request);
    return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 루트 삭제
    @DeleteMapping("/{routeId}")
    public ResponseEntity<?> deleteRoute(
            @PathVariable Long routeId)
    {
        return null;
    }

    // 루트 리스트 조회
    @GetMapping("")
    public ResponseEntity<?> allMyRoute(){
        return null;
    }


}
