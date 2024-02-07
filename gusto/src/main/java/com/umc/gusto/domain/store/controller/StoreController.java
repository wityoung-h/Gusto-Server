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
@RequestMapping("/stores")
public class StoreController {
    private final StoreService storeService;

    /**
     * 가게 1건 조회
     * [GET] /stores/{storeId}
     */
    @GetMapping("/{storeId}")
    public ResponseEntity<StoreResponse.getStore> getStore(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long storeId) {
        User user = authUser.getUser();
        StoreResponse.getStore getStore = storeService.getStore(user, storeId);
        return ResponseEntity.status(HttpStatus.OK).body(getStore);
    }

}
