package com.umc.gusto.domain.store.controller;


import com.umc.gusto.domain.store.model.response.StoreResponse;
import com.umc.gusto.domain.store.service.StoreService;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.global.auth.model.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {
    private final StoreService storeService;

    /**
     * 가게 1건 조회
     * [GET] /stores/{storeId}
     */
    @GetMapping("/{storeId}")
    public ResponseEntity<StoreResponse.getStore> getStore(
            @PathVariable Long storeId) {
        StoreResponse.getStore getStore = storeService.getStore(storeId);
        return ResponseEntity.status(HttpStatus.OK).body(getStore);
    }

}
