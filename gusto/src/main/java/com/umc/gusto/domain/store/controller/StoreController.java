package com.umc.gusto.domain.store.controller;


import com.umc.gusto.domain.store.model.response.StoreResponse;
import com.umc.gusto.domain.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {
    private final StoreService storeService;

    @GetMapping("/{storeId}")
    public ResponseEntity<StoreResponse.getStore> getStore(
            @PathVariable Long storeId) {
        S
    }
    )

}
